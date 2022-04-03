package com.example.instagramclone.fragments

import android.util.Log
import com.example.instagramclone.MainActivity
import com.example.instagramclone.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: FeedFragment() {

    override fun queryPost() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post object
        query.include(Post.KEY_USER)
        // only return posts from currently signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        // return posts in descending order: newer posts will appear first
        query.addDescendingOrder("createdAt")
        // find all post objects
        query.findInBackground(object: FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    // something has went wrong
                    Log.e(MainActivity.TAG, "error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(MainActivity.TAG, "Post: " + post.getDescription() + ", username: " + post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}