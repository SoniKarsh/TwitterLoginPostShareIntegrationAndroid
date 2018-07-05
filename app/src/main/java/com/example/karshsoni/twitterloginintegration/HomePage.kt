package com.example.karshsoni.twitterloginintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_home_page.*


class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val username = intent.getStringExtra("username")
        val uname = findViewById<TextView>(R.id.TV_username)
        TV_userid.text = intent.extras.getLong("userid").toString()
        TV_emailid.text = intent.extras.getString("emailid")
        uname.text = username

    }
}
