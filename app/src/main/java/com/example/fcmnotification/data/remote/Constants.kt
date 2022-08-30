package com.example.fcmnotification.data.remote

/**
 * Declare all constants need to work with rest api
 */
class Constants {

    /**
     * Server key you can get it by opening setting->cloud messaging in firebase
     */
    companion object {
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = "Enter your server key "
        const val CONTENT_TYPE = "application/json"
    }
}