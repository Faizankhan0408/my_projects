package com.faizansocialmediaproject.techchat.Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.MainActivity
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.databinding.FragmentAddPostBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList

class AddPostFragment : Fragment() {
    lateinit var binding: FragmentAddPostBinding
    lateinit var userData :UserDataModel
    lateinit var postData :PostDataModel
    lateinit var imageUri :Uri
    lateinit var progress :ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddPostBinding.inflate(layoutInflater)
        getDataFromDatabase()
        handleOnClicks()

        return binding.root
    }

    private fun process(){
        progress= ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Creating your post")
        progress.setCanceledOnTouchOutside(false)
        progress.show()
    }
    private fun handleOnClicks() {
        binding.btnPost.visibility=View.GONE

        binding.edtDescription.doAfterTextChanged {
            binding.txtDescription.text=it.toString()
            postData.postDesc=it.toString()
        }

        binding.imgGalleryIcon.setOnClickListener {
            setProfileImage()
        }
        binding.btnBack.setOnClickListener {
            val intent =Intent(context,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnPost.setOnClickListener {
            uploadPostWithOther()
        }
    }

    private fun uploadPostWithOther() {
        //progress dialog
        process()
        //filling remaining field of post model
        val time=Date().time.toString()
        postData.postedAt=time
        postData.userID=userData.userId
        postData.userName=userData.userName
        postData.about=userData.about
        postData.userProflieUrl=userData.userProfile

       val database=Firebase.database
       val reference =database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).child(postData.postId).setValue(postData).addOnSuccessListener {
           progress.dismiss()
           val intent=Intent(context,MainActivity::class.java)
           startActivity(intent)
           activity?.finish()
       }.addOnFailureListener{
           progress.dismiss()
           Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
       }
    }

    private fun setProfileImage() {
        val intent =Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),12)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==12){
            if(resultCode== Activity.RESULT_OK){
                if(data!=null){
                    try {
                        imageUri= data.data!!
                        binding.imgPreview.setImageURI(imageUri)
                        callToSaveImageToStorage()
                    }catch (e : Exception){
                        Log.d("error",e.message.toString())
                    }
                }
            }
        }
    }

    private fun callToSaveImageToStorage() {
        val auth =Firebase.auth
        val storege = Firebase.storage
        val key=Firebase.database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).child(auth.uid.toString()).push().key.toString()
        Log.d("check",key)
        val reference = storege.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).child(auth.uid.toString()).child(key)
        reference.putFile(imageUri).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { it->
                Log.d("user",it.toString())
                postData.postUrl=it.toString()

                // for escape of unwanted posts without images
                binding.btnPost.visibility=View.VISIBLE
                //also saving key for post
                postData.postId=key
            }
        }
    }

    private fun getDataFromDatabase() {
        val database = Firebase.database
        val auth= Firebase.auth
        //initializing post data model
        postData =PostDataModel()
        val reference =database.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString()).child(
            GLobalVariable.USER_PROFILE_DETAIL).get().addOnSuccessListener {
            if(it.exists()){
                userData = UserDataModel(it.child("userId").value.toString())
                userData.userName=it.child("userName").value.toString()
                userData.userProfile=it.child("userProfile").value.toString()
                userData.userEmail=it.child("userEmail").value.toString()
                userData.about=it.child("about").value.toString()
                Log.d("user",userData.userId)
                setUserDetailToView()
            }
            else{
                Toast.makeText(context,"Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserDetailToView() {
        binding.txtUserName.text= userData.userName
        binding.txtAbout.text= userData.about
        context?.let { Glide.with(it).load(userData.userProfile).placeholder(R.drawable.ic_person).into(binding.profileImage) }
    }

}