package com.faizansocialmediaproject.techchat.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.Models.UserDataModel
import com.faizansocialmediaproject.techchat.R

class FollowAdapter(private val dataList : List<UserDataModel>,private val context : Context,private val btnName :String) :RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val imgProfile :ImageView =itemView.findViewById(R.id.img_profile)
     val txtName: TextView=itemView.findViewById(R.id.txt_name)
     val txtAbout:TextView=itemView.findViewById(R.id.txt_about)
     val btnFollow :TextView=itemView.findViewById(R.id.btn_follow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.single_item_follower,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=dataList[position]
        holder.txtName.text=model.userName
        holder.txtAbout.text=model.about
        Glide.with(context).load(model.userProfile).placeholder(R.drawable.ic_person).into(holder.imgProfile)
        holder.btnFollow.text=btnName
        holder.btnFollow.isClickable=false
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}