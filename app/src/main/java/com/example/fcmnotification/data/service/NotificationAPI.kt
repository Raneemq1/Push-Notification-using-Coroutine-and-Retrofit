package com.example.fcmnotification.data.service

import com.example.fcmnotification.data.model.PushNotification
import com.example.fcmnotification.data.remote.ApiConstants.Companion.CONTENT_TYPE
import com.example.fcmnotification.data.remote.ApiConstants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    /**
     * suspend function with post request
     * Notify headers because of using our server key so we have to override the header
     */
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}