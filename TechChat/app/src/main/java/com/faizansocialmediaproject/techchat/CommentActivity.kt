package com.faizansocialmediaproject.techchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Models.CommentModel
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.CommentsAdapter
import com.faizansocialmediaproject.techchat.databinding.ActivityCommentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class CommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommentBinding
    lateinit var postModel: PostDataModel
    lateinit var userData: UserDataModel
    lateinit var commentList:ArrayList<CommentModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserDataFromDatabase()
        getDataFromIntent()

        getAllCommentsFromDatabase()

        handleOnClicks()
    }

    private fun getAllCommentsFromDatabase() {
        commentList= ArrayList()
        val database=Firebase.database
        val ref=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).child(postModel.postId).child(GLobalVariable.COMMENTS).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(child in snapshot.children){
                    val model: CommentModel? =child.getValue<CommentModel>()
                    commentList.add(model!!)
                }
                setCommentIntoAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
               Log.d("Error",error.message)
            }

        })
    }

    private fun setCommentIntoAdapter() {
        binding.recycleComment.layoutManager=LinearLayoutManager(this)
        val adapter=CommentsAdapter(commentList,this)
        binding.recycleComment.adapter=adapter
    }

    private fun getUserDataFromDatabase() {
        userData = UserDataModel()
        val database = Firebase.database
        val userId = Firebase.auth.uid.toString()
        val ref = database.getReference(GLobalVariable.DATABASE_NAME).child(userId)
            .child(GLobalVariable.USER_PROFILE_DETAIL).get().addOnSuccessListener {
            if (it.exists()) {
                userData.userId = it.child("userId").value.toString()
                userData.userName = it.child("userName").value.toString()
                userData.userProfile = it.child("userProfile").value.toString()
                userData.userEmail = it.child("userEmail").value.toString()
                userData.about = it.child("about").value.toString()
                setUserValueInView()
                Log.d("user", userData.userId)
            } else {
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserValueInView() {
        binding.edtComment.hint = "Comment as " + userData.userName
        Glide.with(this).load(userData.userProfile).placeholder(R.drawable.ic_person)
            .into(binding.imgProfile)
    }

    private fun handleOnClicks() {
        binding.txtPostBtn.setOnClickListener {
            addCommentIntoDatabase()
        }
        binding.imgBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addCommentIntoDatabase() {
      val comment=CommentModel()
        comment.userName=userData.userName
        comment.userId=userData.userId
        comment.comment=binding.edtComment.text.toString()
        comment.userProfileUrl=userData.userProfile
        comment.commentedAt=Date().time
        binding.edtComment.setText("")
      val database=Firebase.database
      val ref=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS).child(postModel.postId).child(GLobalVariable.COMMENTS).push().setValue(comment)
    }


    private fun getDataFromIntent() {
        postModel = PostDataModel()
        postModel =
            (intent.getSerializableExtra(GLobalVariable.INTENT_POST_DATA) as? PostDataModel)!!

    }
}