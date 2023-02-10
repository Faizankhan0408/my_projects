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
import com.faizansocialmediaproject.techchat.adapters.AllUserListAdapter
import com.faizansocialmediaproject.techchat.databinding.FragmentAllUserFragmnetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AllUserFragmnet : Fragment() {
    lateinit var binding: FragmentAllUserFragmnetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllUserFragmnetBinding.inflate(layoutInflater)

        getAndSetDataFromDatabase()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAndSetDataFromDatabase() {
        val progress=ProgressDialog(context)
        progress.setTitle("Loading")
        progress.show()
        val database = Firebase.database
        val auth = Firebase.auth
        val ref = database.getReference("users")
        val dataList = ArrayList<UserDataModel>()
        val keys = ArrayList<String>()

        binding.recycleAllUser.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { AllUserListAdapter(dataList, it,100) }
        binding.recycleAllUser.adapter = adapter

        // for excluding himself
        keys.add(auth.uid.toString())
        ref.child(auth.uid.toString()).child(GLobalVariable.FOLLOWING).get().addOnSuccessListener { snapShot ->
            if (snapShot.exists()) {
                for (ele in snapShot.children) {
                    val key = ele.key.toString()
                    keys.add(key)
                    Log.d("value", key)
                }
            }
        }
        //fetching all users
        ref.get().addOnSuccessListener { snapShot ->
            progress.dismiss()
            if (snapShot.exists()) {
                for (ele in snapShot.children) {
                    val key = ele.key.toString()

                    if (!keys.contains(key)) {
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

