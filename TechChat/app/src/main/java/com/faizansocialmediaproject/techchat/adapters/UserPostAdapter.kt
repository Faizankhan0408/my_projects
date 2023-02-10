package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Fragments.UserProfileFragment
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.R

class UserPostAdapter(private val postList: List<PostDataModel>, val context: Context):
    RecyclerView.Adapter<UserPostAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_post:ImageView=itemView.findViewById(R.id.img_post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_user_post,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.picture).into(holder.img_post)
    }

    override fun getItemCount(): Int {
       return postList.size
    }
}