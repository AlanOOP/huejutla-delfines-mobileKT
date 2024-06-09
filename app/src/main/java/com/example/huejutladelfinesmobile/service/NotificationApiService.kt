package com.example.huejutladelfinesmobile.service

import com.example.huejutladelfinesmobile.model.NotificationModel
import retrofit2.http.GET

interface NotificationApiService {
    @GET("notification")
    suspend fun getNotifications(): List<NotificationModel>
}