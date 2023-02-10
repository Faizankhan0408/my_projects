package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.ChatRoomActivity
import com.faizansocialmediaproject.techchat.Models.FollowerModel
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AllUserListAdapter(
    private var dataList: ArrayList<UserDataModel>,
    private val context: Context,
    private val specialCode: Int
) :
    RecyclerView.Adapter<AllUserListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_name: TextView = itemView.findViewById(R.id.txt_name)
        val img_profile: ImageView = itemView.findViewById(R.id.img_profile)
        val txt_about: TextView = itemView.findViewById(R.id.txt_about)
        val txt_follow_btn: TextView = itemView.findViewById(R.id.btn_follow)
        val constraint_item :ConstraintLayout=itemView.findViewById(R.id.constraint_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]
        holder.txt_name.text = model.userName.toString()
        holder.txt_about.text = model.about.toString()
        Glide.with(context).load(model.userProfile.toString()).placeholder(R.drawable.ic_person)
            .into(holder.img_profile)

        if (specialCode == 111) {
            holder.txt_follow_btn.visibility = View.GONE
            holder.constraint_item.setOnClickListener {
                val intent= Intent(context,ChatRoomActivity::class.java)
               intent.putExtra(GLobalVariable.INTENT_CHAT_ID,model.userId)
               intent.putExtra(GLobalVariable.INTENT_NAME,model.userName)
                context.startActivity(intent)
            }
        }

        holder.txt_follow_btn.setOnClickListener {
            updateFollowToFollowing(position, holder)
        }
    }

    private fun updateFollowToFollowing(pos: Int, holder: ViewHolder) {
        val database = Firebase.database
        val auth = Firebase.auth
        val modelForFollowing = FollowerModel(dataList[pos].userId, Date().time.toString())
        val modelForFollower = FollowerModel(auth.uid.toString(), Date().time.toString())

        //adding follower into that account
        val reference = database.getReference(GLobalVariable.DATABASE_NAME)
        reference.child(dataList[pos].userId).child(GLobalVariable.FOLLOWER)
            .child(auth.uid.toString()).setValue(modelForFollower)

        //adding following in our account
        reference.child(auth.uid.toString()).child(GLobalVariable.FOLLOWING)
            .child(dataList[pos].userId).setValue(modelForFollowing)
        dataList.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos,dataList.size)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}