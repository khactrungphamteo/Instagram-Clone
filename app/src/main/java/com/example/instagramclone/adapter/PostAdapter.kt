package com.example.instagramclone.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Post
import com.example.instagramclone.R
import org.w3c.dom.Text
import java.security.spec.PSSParameterSpec

class PostAdapter(val context: Context, val posts: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val tvUsername: TextView = itemView.findViewById(R.id.tvUserName)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(post: Post) {
            tvDescription.text = post.getDescription()
            tvUsername.text = post.getUser()?.username

            // populate image image view
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // inflate RecyclerVew with item_tweet.xml
        val view = inflater.inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // Clean all elements of the recycler
    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(postList: MutableList<Post>) {
        posts.addAll(postList)
        notifyDataSetChanged()
    }

}