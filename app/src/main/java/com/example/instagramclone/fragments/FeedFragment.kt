package com.example.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.instagramclone.MainActivity
import com.example.instagramclone.Post
import com.example.instagramclone.R
import com.example.instagramclone.adapter.PostAdapter
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allPosts:MutableList<Post> = mutableListOf<Post>()
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this is where we set up our view
        postsRecyclerView =  view.findViewById<RecyclerView>(R.id.postRecyclerView)
        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer)

        // Steps to populate RecyclerView
        // 1. Create layout for each row in list - already did (item_post.xml)
        // 2. Create data source for each row (this is the Post class)
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecyclerView
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // 5. Set layout manager on RecyclerView

        // Setup refresh listener which triggers new data loading
        // locate the swip container view
        swipeContainer = view.findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            Log.i(TAG, "refreshing timeline")
            queryPost()

        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );

        queryPost()
    }

    // query for all posts in our server
    open fun queryPost() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
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
                        swipeContainer.isRefreshing = false
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "FeedFragment"
    }

}