package com.example.instagramclone

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.instagramclone.fragments.ComposeFragment
import com.example.instagramclone.fragments.FeedFragment
import com.example.instagramclone.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File
import java.util.*

/*
 let user create a post by taking a photo with their camera
 */


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            // create item variable
            item -> var fragmentToShow: Fragment? = null
            // return true saying that we have handled this user interaction
            when (item.itemId) {
                R.id.action_home -> {
                    // TO DO: Navigate to the Home Screen / Feed fragement
                    fragmentToShow = FeedFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.action_compose -> {
                    // TODO: Navigate to the Compose Screen
                    fragmentToShow = ComposeFragment()
                    Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show()
                }

                R.id.action_profile -> {
                    // Navigate to the Profile Screen
                    fragmentToShow = ProfileFragment()
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true

        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home
    }



    companion object {
        const val TAG = "MainActivity"
    }

}