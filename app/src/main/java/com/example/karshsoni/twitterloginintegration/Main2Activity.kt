package com.example.karshsoni.twitterloginintegration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import android.content.Intent
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.models.User
import com.twitter.sdk.android.core.Twitter
import com.squareup.picasso.Picasso


class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Twitter.initialize(this)
        setContentView(R.layout.activity_main2)

        login_button.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                // Do something with result, which provides a TwitterSession for making API calls
                val session = TwitterCore.getInstance().sessionManager.activeSession
                val authToken = session.authToken
                val token = authToken.token
                val secret = authToken.secret
                val authClient = TwitterAuthClient()
                authClient.requestEmail(session, object : Callback<String>() {
                    override fun success(result: Result<String>) {
                        // Do something with the result, which provides the email address
                        login(session, result.data)
                    }

                    override fun failure(exception: TwitterException) {
                        // Do something on failure
                    }
                })
                val userResult = TwitterCore.getInstance().getApiClient(session).accountService.verifyCredentials(true, false, true)
                userResult.enqueue(object : Callback<User>() {
                    override fun success(result: Result<User>) {
                        val user = result.data
                        val profileImage = user.profileImageUrl
                        Picasso.get().load(profileImage).into(imageView)
                        Toast.makeText(this@Main2Activity, ""+user+""+profileImage, Toast.LENGTH_LONG).show()
                    }

                    override fun failure(exception: TwitterException) {
                        Toast.makeText(this@Main2Activity, ""+exception+""+"Failure", Toast.LENGTH_LONG).show()
                    }
                })
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
                Toast.makeText(this@Main2Activity, "Authentication failed!"+exception
                        , Toast.LENGTH_LONG).show()
            }
        }

    }


    fun login(session: TwitterSession, emailId:String) {

        val username = session.userName
        val userId = session.userId

        val intent = Intent(this@Main2Activity, HomePage::class.java)
        intent.putExtra("username", username)
        intent.putExtra("userid", userId)
        intent.putExtra("emailid", emailId)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result to the login button.
        login_button.onActivityResult(requestCode, resultCode, data)
    }

}

