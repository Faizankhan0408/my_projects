package com.faizansocialmediaproject.techchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizansocialmediaproject.techchat.Models.MessageModel
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.faizansocialmediaproject.techchat.adapters.MessageAdapter
import com.faizansocialmediaproject.techchat.databinding.ActivityChatRoomBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import java.util.*
import kotlin.collections.ArrayList

class ChatRoomActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatRoomBinding
    lateinit var otherId :String
    lateinit var messageList :ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromIntent()
        OnClicks()
        getChatDataFromDatabase()
    }

    private fun getDataFromIntent() {
        val intent=getIntent()
        otherId= intent.getStringExtra("chatId")!!
        val name=intent.getStringExtra(GLobalVariable.INTENT_NAME)
        binding.txtName.text=name
    }

    private fun getChatDataFromDatabase() {
        val userId=Firebase.auth.uid.toString()
        val database=Firebase.database
        val senderRoom=userId+otherId
        messageList=ArrayList()
        val ref=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.CHAT).child(senderRoom).child(GLobalVariable.MESSAGE).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(child in snapshot.children){
                    val message: MessageModel? =child.getValue<MessageModel>()
                    Log.d("message", message!!.text)

                    messageList.add(message)
                }
                setDataToAdapter()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataToAdapter() {
        binding.recycleChat.layoutManager=LinearLayoutManager(this)
        val adapter=MessageAdapter(messageList,this)
        binding.recycleChat.adapter=adapter
    }

    private fun OnClicks() {
        binding.cardViewChat.setBackgroundResource(R.drawable.curved_btn_background)

        binding.imgSend.setOnClickListener {
            if(binding.edtChat.text.toString().isNotEmpty()){
                saveMessageToDatabase()
            }
        }
        binding.imgBack.setOnClickListener {
            val intent=Intent(this,ChatActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveMessageToDatabase() {
        val database=Firebase.database
        val userId=Firebase.auth.uid.toString()
        val message=MessageModel()
        message.senderId=userId
        message.sendAt=Date().time
        message.text=binding.edtChat.text.toString()
        binding.edtChat.setText("")

        val senderRoom=userId+otherId
        val receiverRoom=otherId+userId

        val rootRef=database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.CHAT)
        val senderRef=rootRef.child(senderRoom).child(GLobalVariable.MESSAGE).push().setValue(message).addOnSuccessListener {
            val receiverRef=rootRef.child(receiverRoom).child(GLobalVariable.MESSAGE).push().setValue(message)
        }
    }
}