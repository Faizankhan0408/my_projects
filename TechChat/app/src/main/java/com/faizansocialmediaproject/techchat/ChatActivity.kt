package com.faizansocialmediaproject.techchat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.AllUserListAdapter
import com.faizansocialmediaproject.techchat.databinding.ActivityChatBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var userList: ArrayList<UserDataModel>
    lateinit var searchingList :ArrayList<UserDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getListOfAllUsers()
        onClicks()
    }

    private fun onClicks() {
   binding.edtSearch.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
       override fun onQueryTextSubmit(query: String?): Boolean {
          TODO()
       }

       @SuppressLint("NotifyDataSetChanged")
       override fun onQueryTextChange(newText: String?): Boolean {
          searchingList.clear()
           val searchText=newText!!.toLowerCase(Locale.getDefault())
           if(searchText.isNotEmpty()){
               userList.forEach{
                   if(it.userName.toLowerCase(Locale.getDefault()).contains(searchText)){
                       searchingList.add(it)
                   }
               }
               binding.recycleChat.adapter?.notifyDataSetChanged()
           }else{
               searchingList.clear()
               searchingList.addAll(userList)
               binding.recycleChat.adapter?.notifyDataSetChanged()
           }

           return false
       }

   })
    }

    private fun setAdapter(){
    binding.recycleChat.layoutManager=LinearLayoutManager(this)
    val adapter=AllUserListAdapter(searchingList,this,111)
    binding.recycleChat.adapter=adapter
}
    private fun getListOfAllUsers() {
        userList = ArrayList()
        searchingList= ArrayList()
        val uid=Firebase.auth.uid.toString()
        val database = Firebase.database
        val ref = database.getReference(GLobalVariable.DATABASE_NAME).get()
            .addOnSuccessListener { snapShot ->
                if (snapShot.exists()) {
                    for (ele in snapShot.children) {
                      val key=ele.key.toString()
                        if(key==uid)
                            continue
                        val model =
                            ele.child("user profile detail").getValue(UserDataModel::class.java)
                        model?.let { userList.add(it) }
                        if (model != null) {
                            Log.d("key", model.userName.toString())
                        }
                    }
                    searchingList.addAll(userList)
                    setAdapter()
                }
                Log.d("key", userList.size.toString())
            }
    }
}
