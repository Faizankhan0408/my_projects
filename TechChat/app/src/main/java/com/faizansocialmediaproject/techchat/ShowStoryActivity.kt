package com.faizansocialmediaproject.techchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Models.UserStoryModel
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.databinding.ActivityShowStoryBinding

class ShowStoryActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowStoryBinding
    lateinit var model :UserStoryModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
         getDataFromIntent()
        setDataOnView()
        onClicks()
    }

    private fun onClicks() {
        binding.imgBack.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDataOnView() {
        binding.txtUserName.text=model.userName
        Glide.with(this).load(model.userProfileUrl).placeholder(R.drawable.ic_person).into(binding.imgStory)
        Glide.with(this).load(model.userStoryUrl).placeholder(R.drawable.picture).into(binding.imgBigStory)
    }

    private fun getDataFromIntent() {
        model= UserStoryModel()
         model= (intent.getSerializableExtra(GLobalVariable.INTENT_STORY_DATA) as? UserStoryModel)!!
    }
}