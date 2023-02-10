package com.faizansocialmediaproject.techchat.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Credential.LoginActivity
import com.faizansocialmediaproject.techchat.EditProfileActivity
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.UserPostAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class UserProfileFragment : Fragment() {
    lateinit var auth : FirebaseAuth
    lateinit var storage : FirebaseStorage
    lateinit var binding : FragmentUserProfileBinding
    val REQUEST_CODE=1
    lateinit var imageUri : Uri
    lateinit var postList:ArrayList<PostDataModel>

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
        this.getAllUserPosts()
        handleClicks()
        return view
    }

    private fun getAllUserPosts() {
        postList= ArrayList()
        val database=Firebase.database
        val userId=Firebase.auth.uid.toString()
        val ref=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(post in snapshot.children){
                    val value=post.child("userID").value.toString()
                    Log.d("value",value)
                    if(value==userId){
                        val model:PostDataModel= post.getValue<PostDataModel>()!!
                        postList.add(model)
                    }
                }
                var postCount=postList.size
                binding.txtPostCount.text=postCount.toString()
                setDataToAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setDataToAdapter() {
        binding.recycleUserPosts.layoutManager=GridLayoutManager(context,3,LinearLayoutManager.VERTICAL,false)
        val adapter= context?.let { UserPostAdapter(postList, it) }
        binding.recycleUserPosts.hasFixedSize()
        binding.recycleUserPosts.adapter=adapter
    }

    private fun setUserDetailToView(
        userData: UserDataModel,
        followerCount: Int,
        followingCount: Int
    ) {
        binding.txtUserName.text= userData.userName
        binding.txtAbout.text= userData.about
        context?.let { Glide.with(it).load(userData.userProfile).placeholder(R.drawable.ic_person).into(binding.imgProfile) }
        binding.txtFollowerCount.text=followerCount.toString()
        binding.txtFollowingCount.text=followingCount.toString()
    }

    private fun getDataFromDatabase() {
        val database =Firebase.database
        val reference =database.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString()).get().addOnSuccessListener {
            if(it.exists()){
                val ele=it.child(GLobalVariable.USER_PROFILE_DETAIL)
              val userData = UserDataModel(ele.child("userId").value.toString())
              userData.userName=ele.child("userName").value.toString()
              userData.userProfile=ele.child("userProfile").value.toString()
              userData.userEmail=ele.child("userEmail").value.toString()
              userData.about=ele.child("about").value.toString()
                Log.d("user",userData.userId)
                val followers=it.child(GLobalVariable.FOLLOWER)
                var followerCount=0
                for(child in followers.children){
                    followerCount+=1
                }
                val followings=it.child(GLobalVariable.FOLLOWING)
                var followingCount=0
                for(child in followings.children){
                    followingCount+=1
                }

              setUserDetailToView(userData,followerCount,followingCount)
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
            showDialog()
        }
        binding.imgProfile.setOnClickListener{
            setProfileImage()
        }
        binding.txtEditProfile.setOnClickListener{
            val intent= Intent(context,EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog() {
        val customDialog = Dialog(requireActivity())
        customDialog.setContentView(R.layout.custom_dialog)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val yesBtn = customDialog.findViewById(R.id.yes_opt) as TextView
        val noBtn = customDialog.findViewById(R.id.no_opt) as TextView
        yesBtn.setOnClickListener {
            //Do something here
            logoutAccount()
        }
        noBtn.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
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
      storage = Firebase.storage
       val reference = storage.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString()).child(GLobalVariable.USER_PROFILE_DETAIL)
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





