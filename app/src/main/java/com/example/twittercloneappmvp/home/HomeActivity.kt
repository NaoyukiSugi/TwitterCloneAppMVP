package com.example.twittercloneappmvp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.twittercloneappmvp.R
import dagger.hilt.android.AndroidEntryPoint

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
