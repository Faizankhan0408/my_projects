package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Models.UserStoryModel
import com.faizansocialmediaproject.techchat.R

class StoryAdapter(private val storyList: List<UserStoryModel>, private val context: Context) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val img_story : ImageView = itemView.findViewById(R.id.img_story)
        val txt_userName : TextView =itemView.findViewById(R.id.txt_userName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_story_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val model =storyList[position]
        holder.txt_userName.text=model.userName
        Glide.with(context).load(model.userProfileUrl).placeholder(R.drawable.ic_person).into(holder.img_story)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }
}