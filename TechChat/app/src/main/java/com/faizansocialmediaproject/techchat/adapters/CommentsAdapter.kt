package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Models.CommentModel
import com.faizansocialmediaproject.techchat.R

class CommentsAdapter(private val commentList:ArrayList<CommentModel>,private val context: Context) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val img_profile:ImageView=itemView.findViewById(R.id.img_story)
      val txt_comment:TextView=itemView.findViewById(R.id.txt_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_comment_item,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model=commentList[position]
        val comment="<span><b>${model.userName}</b></span><p>${model.comment}</p>"
        holder.txt_comment.text= Html.fromHtml(comment,Html.FROM_HTML_MODE_LEGACY)
        Glide.with(context).load(model.userProfileUrl).placeholder(R.drawable.ic_person).into(holder.img_profile)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}