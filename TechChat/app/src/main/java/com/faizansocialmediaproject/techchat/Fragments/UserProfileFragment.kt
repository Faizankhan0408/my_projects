package com.faizansocialmediaproject.techchat.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Credential.LoginActivity
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class UserProfileFragment : Fragment() {
    lateinit var auth : FirebaseAuth
    lateinit var storege : FirebaseStorage
    lateinit var binding : FragmentUserProfileBinding
    val REQUEST_CODE=1
    lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("UseSupportActionBar")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        val view=binding.root

        getDataFromDatabase()

        handleClicks()
        return view
    }

    private fun setUserDetailToView(userData: UserDataModel) {
        binding.txtUserName.text= userData.userName
        binding.txtAbout.text= userData.about
        context?.let { Glide.with(it).load(userData.userProfile).placeholder(R.drawable.ic_person).into(binding.imgProfile) }
    }

    private fun getDataFromDatabase() {
        val database =Firebase.database
        val reference =database.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString()).child(GLobalVariable.USER_PROFILE_DETAIL).get().addOnSuccessListener {
            if(it.exists()){
              val userData = UserDataModel(it.child("userId").toString())
              userData.userName=it.child("userName").value.toString()
              userData.userProfile=it.child("userProfile").value.toString()
              userData.userEmail=it.child("userEmail").value.toString()
                Log.d("user",userData.userId)
              setUserDetailToView(userData)
            }
            else{
                Toast.makeText(context,"Failed to fetch data",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleClicks() {
        binding.btnLogout.setOnClickListener{
            logoutAccount()
        }
        binding.imgProfile.setOnClickListener{
            setProfileImage()
        }
    }

    private fun setProfileImage() {
      val intent =Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE){
            if(resultCode==Activity.RESULT_OK){
                if(data!=null){
                    try {
                        imageUri= data.data!!
                        binding.imgProfile.setImageURI(imageUri)
                        callToSaveImageToStorage()
                    }catch (e : Exception){
                        Log.d("error",e.message.toString())
                    }
                }
            }
        }
    }

    private fun callToSaveImageToStorage() {
      storege = Firebase.storage
       val reference = storege.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString()).child(GLobalVariable.USER_PROFILE_DETAIL)
         reference.putFile(imageUri).addOnSuccessListener {
             reference.downloadUrl.addOnSuccessListener { it->
               val map : MutableMap<String,Any> = mutableMapOf<String,Any>()
                 map["userProfile"] = it.toString()
                 Log.d("user",it.toString())
                 val database =Firebase.database
                 val reference=database.getReference(GLobalVariable.DATABASE_NAME)

                 reference.child(auth.uid.toString()).child(GLobalVariable.USER_PROFILE_DETAIL).updateChildren(map).addOnCompleteListener{
                         task->
                     run {
                         if (task.isSuccessful) {
                             Log.d("user", "completed")
                         }
                         else{
                             Log.d("user",task.exception?.message.toString())
                         }
                     }
                 }

             }
         }
    }

    private fun logoutAccount() {
        Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
        Log.d("user","logout")
        auth.signOut()
        val intent = Intent(context,LoginActivity::class.java)
        startActivity(intent)
    }

}




