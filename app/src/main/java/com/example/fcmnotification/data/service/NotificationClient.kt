package com.example.fcmnotification.data.service


import com.example.fcmnotification.data.model.PushNotification
import com.example.fcmnotification.data.remote.ApiConstants.Companion.BASE_URL
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Build a single object of notification client that build a retrofit builder
 * User api interface to post notification
 */
object NotificationClient {

    private val api: NotificationAPI

    init {
        /**
         * Initialize retrofit instance only when it needs
         */
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
        api = retrofit.create(NotificationAPI::class.java)

    }

    /**
     * This method uses api instance to invoke post notification function from api interface
     */
    suspend fun postData(notification: PushNotification): Response<ResponseBody> {
        return api.postNotification(notification)
    }


}