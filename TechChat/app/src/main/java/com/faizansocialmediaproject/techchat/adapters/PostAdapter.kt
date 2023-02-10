package com.faizansocialmediaproject.techchat.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizansocialmediaproject.techchat.CommentActivity
import com.faizansocialmediaproject.techchat.Models.LikeModel
import com.faizansocialmediaproject.techchat.Models.PostDataModel
import com.faizansocialmediaproject.techchat.R
import com.faizansocialmediaproject.techchat.Tools.GLobalVariable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class PostAdapter(private val postList: List<PostDataModel>, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_userName: TextView = itemView.findViewById(R.id.txt_userName)
        val txt_about: TextView = itemView.findViewById(R.id.txt_about)
        val txt_desc: TextView = itemView.findViewById(R.id.txt_desc)
        val img_profile: ImageView = itemView.findViewById(R.id.img_profile)
        val img_post: ImageView = itemView.findViewById(R.id.img_post)
        val card_post: CardView = itemView.findViewById(R.id.card_view_post)
        val img_like: ImageView = itemView.findViewById(R.id.img_like)
        val img_comment: ImageView = itemView.findViewById(R.id.img_comment)
        val img_share: ImageView = itemView.findViewById(R.id.img_share)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = postList[position]
        holder.txt_userName.text = model.userName
        holder.txt_about.text = model.about
        if (model.postDesc.isEmpty()) {
            holder.txt_desc.visibility = View.GONE
        } else {
            holder.txt_desc.text = model.postDesc
        }

        checkIfAlreadyLike(holder, model)

        Glide.with(context).load(model.userProflieUrl).placeholder(R.drawable.ic_person)
            .into(holder.img_profile)
        Glide.with(context).load(model.postUrl).placeholder(R.drawable.picture)
            .into(holder.img_post)
        holder.card_post.setBackgroundResource(R.drawable.bg_for_post_card)

        holder.img_share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey let's checkout this amazing post :" + model.postUrl
            )
            context.startActivity(intent)
        }
        holder.img_like.setOnClickListener {
            changeStateOfLikeButton(holder, model)
        }
        holder.img_comment.setOnClickListener {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra(GLobalVariable.INTENT_POST_DATA, model)
            context.startActivity(intent)
        }

    }

    private fun checkIfAlreadyLike(holder: ViewHolder, model: PostDataModel) {
        val id = Firebase.auth.uid.toString()
        val database = Firebase.database
        val ref = database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS)
            .child(model.postId).child(GLobalVariable.LIKE)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        val key = child.key.toString()
                        if (key == id) {
                            Glide.with(context).load(R.drawable.heart_red).into(holder.img_like)
                            break
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }

    private fun changeStateOfLikeButton(holder: ViewHolder, model: PostDataModel) {
        Glide.with(context).load(R.drawable.heart_red).into(holder.img_like)
        val userId = Firebase.auth.uid.toString()
        val likeModel = LikeModel()
        likeModel.likeAt = Date().time
        likeModel.likerId = userId
        val database = Firebase.database
        val ref = database.getReference(GLobalVariable.DATABASE_NAME).child(GLobalVariable.POSTS)
            .child(model.postId).child(GLobalVariable.LIKE).child(userId).setValue(likeModel)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}


