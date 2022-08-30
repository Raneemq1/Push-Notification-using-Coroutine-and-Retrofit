package com.example.fcmnotification.data.model

/**
 * This push notification contains data that will send
 * To can be one of the following two cases
 * Topic which will cover all users
 * Token to determine specific user
 * */
data class PushNotification (val data:Notification,val to:String)