package com.faizansocialmediaproject.techchat.Credential

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.faizansocialmediaproject.techchat.MainActivity
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {
    lateinit var userDataModel: UserDataModel
    lateinit var binding : ActivitySignupBinding
   lateinit var auth : FirebaseAuth

   lateinit var email : String
   lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnLogin.setOnClickListener{
           createUserAccount()
        }
    }

    private fun createUserAccount() {
        email = binding.edtUserEmail.editText?.text.toString()
        Log.d("user",email)
        password = binding.edtUserPassword.editText?.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    user?.let {
                        userDataModel = UserDataModel(user.uid)
                        getValuesFromView()
                        userDataModel.userId=user.uid
                        GLobalVariable.UserId=user.uid
                    }
                    saveDataToDatabase()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveDataToDatabase() {
        val firebase = Firebase.database
        val reference =firebase.getReference(GLobalVariable.DATABASE_NAME)
        reference.child(GLobalVariable.UserId).child(GLobalVariable.USER_PROFILE_DETAIL).setValue(userDataModel).addOnCompleteListener{
            task->
            run {
                if (task.isSuccessful) {
                    Log.d("user", "completed")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Log.d("user",task.exception?.message.toString())
                }
            }
        }

    }

    private fun getValuesFromView() {
        userDataModel.userName=(binding.edtUserName.editText?.text.toString())
        userDataModel.userEmail=(binding.edtUserEmail.editText?.text.toString())
        userDataModel.userPassword=(binding.edtUserName.editText?.text.toString())
    }
}