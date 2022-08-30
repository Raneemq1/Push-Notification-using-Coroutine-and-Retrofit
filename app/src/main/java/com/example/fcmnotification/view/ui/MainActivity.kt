package com.example.fcmnotification.view.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmnotification.R
import com.example.fcmnotification.data.model.Notification
import com.example.fcmnotification.data.model.PushNotification
import com.example.fcmnotification.data.remote.Constants.Companion.TAG
import com.example.fcmnotification.data.remote.Constants.Companion.TOPIC
import com.example.fcmnotification.data.remote.Constants.Companion.token1
import com.example.fcmnotification.data.service.NotificationClient
import com.example.fcmnotification.notifications.FirebaseService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    /**
     * Declare ui components
     */
    private lateinit var etMessage: EditText
    private lateinit var etTitle: EditText
    private lateinit var etToken: EditText
    private lateinit var btnSend: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMainComponent()

        initSharePrefOfFirebaseService()

        initFirebaseMessaging()


        btnSend.setOnClickListener {
            initNotificationDetails()
        }

    }

    /**
     * Initialize ui components
     */
    private fun initMainComponent() {
        etMessage = findViewById(R.id.bodyET)
        etTitle = findViewById(R.id.titleET)
        etToken = findViewById(R.id.tokenET)
        btnSend = findViewById(R.id.sendBtn)
    }

    /**
     * Initialize share preference of firebase Service
     */
    private fun initSharePrefOfFirebaseService() {
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    /**
     * Assign token edit text with other device token
     */
    private fun initFirebaseMessaging() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            FirebaseService.token = token
            Log.d("Token", token.toString())
            etToken.setText(token1)
        }


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    /**
     * Get the notification details
     * Decide the push notification receiver by converting between TOPIC and recipientToken
     */
    private fun initNotificationDetails() {
        val title = etTitle.text.toString()
        val message = etMessage.text.toString()
        val recipientToken = etToken.text.toString()
        if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
            PushNotification(
                Notification(title, message),
                recipientToken
            ).also {
                sendNotification(it)
            }
        }
    }


    /**
     * Invoke of suspend function from notification api interface declare a coroutine scope
     * Choose IO dispatcher because of sending an output data
     */
    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NotificationClient.postData(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }


}