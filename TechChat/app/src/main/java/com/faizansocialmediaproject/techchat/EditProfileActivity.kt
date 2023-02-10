package com.faizansocialmediaproject.techchat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding
    lateinit var imageUri: Uri
    private var profileUrl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClicks()

    }

    private fun handleClicks() {
        var flag = 0
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.imgProfile.setOnClickListener {
            flag = 1
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11)
        }
        binding.btnUpdateProfile.setOnClickListener {
            editProfile(flag)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        imageUri = data.data!!
                        binding.imgProfile.setImageURI(imageUri)
                        callToSaveImageToStorage()
                    } catch (e: Exception) {
                        Log.d("error", e.message.toString())
                    }
                }
            }
        }
    }

    private fun callToSaveImageToStorage() {
        val auth = Firebase.auth
        val storage = Firebase.storage
        val reference =
            storage.getReference(GLobalVariable.DATABASE_NAME).child(auth.uid.toString())
                .child(GLobalVariable.USER_PROFILE_DETAIL)
        reference.putFile(imageUri).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { it ->
                profileUrl = it.toString()
            }
        }
    }


    private fun editProfile(flag: Int) {
        val database = Firebase.database
        val auth = Firebase.auth
        val name = binding.edtName.text.toString()
        val about = binding.edtAbout.text.toString()


        val childUpdates: MutableMap<String, Any> = mutableMapOf()
        childUpdates["userName"] = name
        childUpdates["about"] = about
        if (flag == 1) {
            childUpdates["userProfile"] = profileUrl
        }
        val reference = database.getReference(GLobalVariable.DATABASE_NAME)

        reference.child(auth.uid.toString()).child(GLobalVariable.USER_PROFILE_DETAIL)
            .updateChildren(childUpdates).addOnCompleteListener { task ->
                run {
                    if (task.isSuccessful) {
                        Log.d("user", "completed")
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("user", task.exception?.message.toString())
                    }
                }
            }
    }

}