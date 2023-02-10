package com.faizansocialmediaproject.techchat.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.FollowAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentFollowingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FollowingFragment : Fragment() {
    lateinit var binding: FragmentFollowingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFollowingBinding.inflate(layoutInflater)

        getDataAndSetDataFromDatabase()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataAndSetDataFromDatabase() {
        val progress=ProgressDialog(context)
        progress.setTitle("Loading")
        progress.show()

        val database = Firebase.database
        val auth = Firebase.auth
        val dataList = ArrayList<UserDataModel>()
        val keys = ArrayList<String>()

        // setting adapter and recycleView
        binding.recycleFollowing.layoutManager= LinearLayoutManager(context)
        val adapter= context?.let { FollowAdapter(dataList, it,"following") }
        binding.recycleFollowing.adapter=adapter

        //getting data from database
        val ref = database.getReference(GLobalVariable.DATABASE_NAME)
        ref.child(auth.uid.toString()).child(GLobalVariable.FOLLOWING).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (ele in dataSnapshot.children) {
                        val key = ele.key.toString()
                        Log.d("key",key)
                        keys.add(key)
                    }
                }
            }

        //fetching all followers
        ref.get().addOnSuccessListener { snapShot ->
            progress.dismiss()
            if (snapShot.exists()) {
                for (ele in snapShot.children) {
                    val key = ele.key.toString()

                    if (keys.contains(key)) {
                        val model = ele.child("user profile detail").getValue(UserDataModel::class.java)
                        model?.let { dataList.add(it) }
                        if (model != null) {
                            Log.d("key", model.userName.toString())
                        }
                    }
                }
                adapter!!.notifyDataSetChanged()
                Log.d("key", dataList.size.toString())
            }
        }.addOnFailureListener {
            progress.dismiss()
            println(it.message)
        }

    }
}