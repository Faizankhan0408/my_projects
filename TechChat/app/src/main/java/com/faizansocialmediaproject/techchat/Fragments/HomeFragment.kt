package com.faizansocialmediaproject.techchat.Fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.ChatActivity
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.Models.UserStoryModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.ShowStoryActivity
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.PostAdapter
import com.faizansocialmediaproject.techchat.adapters.StoryAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var postList: ArrayList<PostDataModel>
    lateinit var storyList: ArrayList<UserStoryModel>
    lateinit var storyUri: Uri
    lateinit var userData: UserDataModel
    lateinit var followerList: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getInstances()
        getUserBioFromDatabase()
        getDataForStories()
        getDataForPosts()
        onClicks()

        return binding.root
    }

    private fun getDataForStories() {
        followerList = ArrayList<String>()
        storyList = ArrayList()
        val myId = auth.uid.toString()
        val rootRef = database.getReference(GLobalVariable.DATABASE_NAME)

        followerList.add(auth.uid.toString())
        val referenceForFollower =
            rootRef.child(auth.uid.toString()).child(GLobalVariable.FOLLOWER).get()
                .addOnSuccessListener { dataSnap ->
                    if (dataSnap.exists()) {
                        for (child in dataSnap.children) {
                            val key = child.key.toString()
                            followerList.add(key)
                            Log.d("KEY", key)
                        }
                    }
                }
        val referenceForStories =
            rootRef.child(GLobalVariable.STORY).get().addOnSuccessListener { dataSnap ->
                if (dataSnap.exists()) {
                    for (child in dataSnap.children) {
                        val key = child.key.toString()
                        if (child.exists() && child.value != null) {
                            if (key == myId) {
                                val model = UserStoryModel()
                                model.postedAt = child.child("postedAt").value.toString().toLong()
                                model.userId = child.child("userId").value.toString()
                                model.userName = child.child("userName").value.toString()
                                Log.d("Story", model.userName)
                                model.userProfileUrl =
                                    child.child("userProfileUrl").value.toString()
                                model.userStoryUrl = child.child("userStoryUrl").value.toString()
                                notifyMeToChangeMyStoryView(model)
                            } else if (followerList.contains(key) && key != myId) {
                                val model = UserStoryModel()
                                model.postedAt = child.child("postedAt").value.toString().toLong()
                                model.userId = child.child("userId").value.toString()
                                model.userName = child.child("userName").value.toString()
                                Log.d("Story", model.userName)
                                model.userProfileUrl =
                                    child.child("userProfileUrl").value.toString()
                                model.userStoryUrl = child.child("userStoryUrl").value.toString()
                                storyList.add(model)
                            }
                        }
                    }
                    setDataToStoryAdapter()
                }
            }
    }


    private fun notifyMeToChangeMyStoryView(model: UserStoryModel) {
        val timeDiff : Long=Date().time-model.postedAt
        if(timeDiff<GLobalVariable.TIME_DIFF){
            binding.imgStory.visibility = View.INVISIBLE
            binding.txtUserName.visibility = View.INVISIBLE
            binding.imgStoryReplace.visibility = View.VISIBLE
            binding.txtUserNameReplace.visibility = View.VISIBLE
            //setting values on view
            binding.txtUserNameReplace.text=model.userName
            context?.let { Glide.with(it).load(model.userStoryUrl).placeholder(R.drawable.ic_person).into(binding.imgStoryReplace) }
            binding.imgStoryReplace.setOnClickListener {
                //open it in different activity
                val intent=Intent(context,ShowStoryActivity::class.java)
                intent.putExtra(GLobalVariable.INTENT_STORY_DATA,model)
                startActivity(intent)
            }
        }
        else{
            binding.imgStory.visibility = View.VISIBLE
            binding.txtUserName.visibility = View.VISIBLE
            binding.imgStoryReplace.visibility = View.GONE
            binding.txtUserNameReplace.visibility = View.GONE
            val ref=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.STORY).child(model.userId).removeValue()
        }
    }

    private fun setDataPostDataToAdapter() {
        Log.d("KEY", postList.size.toString())
        binding.recyclePosts.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { PostAdapter(postList, it) }
        binding.recyclePosts.adapter = adapter
    }

    private fun getDataForPosts() {
        postList = ArrayList<PostDataModel>()

        val rootRef = database.getReference(GLobalVariable.DATABASE_NAME)

        val referenceForPosts =
            rootRef.child(GLobalVariable.POSTS).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (ele in dataSnapshot.children) {
                        val child = ele.child("userID").value.toString()
                        Log.d("KEY", child)
                        if (followerList.contains(child)) {
                            val model = PostDataModel()
                            model.userName = ele.child("userName").value.toString()
                            Log.d("KEY", model.userName)
                            model.userID = ele.child("userID").value.toString()
                            model.userProflieUrl = ele.child("userProflieUrl").value.toString()
                            model.about = ele.child("about").value.toString()
                            model.postId = ele.child("postId").value.toString()
                            model.postUrl = ele.child("postUrl").value.toString()
                            model.postedAt = (ele.child("postedAt").value.toString())
                            model.postDesc = ele.child("postDesc").value.toString()
                            postList.add(model)
                        }
                    }
                    postList.sortedBy { it.postedAt }
                    postList.reverse()
                    setDataPostDataToAdapter()
                }

            }

    }

    private fun setDataToStoryAdapter() {
        binding.recycleStory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val storyAdapter = context?.let { StoryAdapter(storyList, it) }
        binding.recycleStory.adapter = storyAdapter
    }

    private fun getInstances() {
        auth = Firebase.auth
        storage = Firebase.storage
        database = Firebase.database
    }

    private fun onClicks() {
        binding.imgStory.setOnClickListener {
            pickImageFromGallery()
        }
        binding.imgChatBtn.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 13)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        storyUri = data.data!!
                        binding.imgStory.setImageURI(storyUri)
                        callToSaveImageToStorage()
                    } catch (e: Exception) {
                        Log.d("error", e.message.toString())
                    }
                }
            }
        }
    }

    private fun getUserBioFromDatabase() {
        val database = Firebase.database
        val reference =
            database.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString())
                .child(GLobalVariable.USER_PROFILE_DETAIL).get().addOnSuccessListener {
                    if (it.exists()) {
                        userData = UserDataModel(it.child("userId").value.toString())
                        userData.userName = it.child("userName").value.toString()
                        userData.userProfile = it.child("userProfile").value.toString()
                        userData.userEmail = it.child("userEmail").value.toString()
                        userData.about = it.child("about").value.toString()
                        Log.d("user", userData.userId)
                        setDataToStoryOption()
                    } else {
                        Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
    }

    private fun setDataToStoryOption() {
        context?.let {
            Glide.with(it).load(userData.userProfile).placeholder(R.drawable.ic_person)
                .into(binding.imgStory)
        }
    }

    private fun callToSaveImageToStorage() {
        storage = Firebase.storage
        val reference =
            storage.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString())
                .child(GLobalVariable.STORY)
        reference.putFile(storyUri).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { it ->
                val model = UserStoryModel()
                model.postedAt = Date().time
                model.userId = userData.userId
                model.userName = userData.userName
                model.userProfileUrl = userData.userProfile
                model.userStoryUrl = it.toString()
                val database = Firebase.database
                val reference = database.getReference(GLobalVariable.DATABASE_NAME)
                reference.child(GLobalVariable.STORY).child(auth.uid.toString()).setValue(model)
            }
        }
    }

}