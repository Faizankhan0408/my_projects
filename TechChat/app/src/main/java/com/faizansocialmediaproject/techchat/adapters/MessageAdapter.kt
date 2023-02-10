package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faizansocialmediaproject.techchat.Models.MessageModel
import com.faizansocialmediaproject.techchat.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageAdapter(private val messageList:ArrayList<MessageModel>,private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SENT:Int=1
    val ITEM_RECEIVE:Int=2

    public class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txt_send :TextView=itemView.findViewById(R.id.txt_send)
    }

    public class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txt_receive :TextView=itemView.findViewById(R.id.txt_receive)
    }

    override fun getItemViewType(position: Int): Int {
        val message=messageList[position]
        val userId=Firebase.auth.uid.toString()
        if(userId==message.senderId){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==ITEM_SENT){
            val view=LayoutInflater.from(context).inflate(R.layout.layout_for_send,parent,false)
            return SendViewHolder(view)
        }
        else{
            val view =LayoutInflater.from(context).inflate(R.layout.layout_for_receive,parent,false)
            return ReceiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message=messageList[position]
       if(message.senderId==Firebase.auth.uid.toString()){
           val viewHolder:SendViewHolder= holder as SendViewHolder
           viewHolder.txt_send.text=message.text
       }
        else {
            val viewHolder :ReceiverViewHolder=holder as ReceiverViewHolder
            viewHolder.txt_receive.text=message.text
        }
       }

    override fun getItemCount(): Int {
        return messageList.size
    }
}



