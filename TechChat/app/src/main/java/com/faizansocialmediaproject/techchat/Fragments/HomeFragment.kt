package com.faizansocialmediaproject.techchat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizansocialmediaproject.techchat.Models.UserStoryModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.adapters.StoryAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage


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
        setDataToAdapter()
        onClicks()

        return  binding.root
    }

    private fun setDataToAdapter() {
        // setting adapter
        val storyList = setDataToArrayList()
        binding.recycleStory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val storyAdapter = context?.let { StoryAdapter(storyList, it) }
        binding.recycleStory.adapter = storyAdapter
    }

    private fun setDataToArrayList(): List<UserStoryModel> {
        val storyList = ArrayList<UserStoryModel>()
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "faizan",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "faizan khan",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "faiz",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "zan",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "faizan",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        storyList.add(
            UserStoryModel(
                "1",
                "https://source.unsplash.com/user/c_v_r",
                "faizan",
                "https://source.unsplash.com/user/c_v_r"
            )
        )
        return storyList
    }

    private fun getInstances() {
        auth = Firebase.auth
        storage = Firebase.storage
        database = Firebase.database
    }

    private fun onClicks() {
    }

}