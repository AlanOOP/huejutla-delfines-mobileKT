package com.example.huejutladelfinesmobile.service

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.example.huejutladelfinesmobile.model.Schedule
import com.google.android.gms.wearable.DataMap

fun sendSchedulesToWear(context: Context, schedules: List<Schedule>) {
    val dataClient = Wearable.getDataClient(context)
    val putDataMapRequest = PutDataMapRequest.create("/schedule")

    val scheduleDataMap = putDataMapRequest.dataMap
    val scheduleList = schedules.map {
        val dataMap = DataMap()
        dataMap.putString("id", it._id)
        dataMap.putString("day", it.day)
        dataMap.putString("hour", it.hour)
        dataMap
    }
    scheduleDataMap.putDataMapArrayList("schedules", ArrayList(scheduleList))

    val putDataRequest = putDataMapRequest.asPutDataRequest()
    dataClient.putDataItem(putDataRequest).addOnSuccessListener {
        // Successfully sent data
        println(
            "Successfully sent data to Wearable: ${schedules.size} schedules"
        )
    }.addOnFailureListener {
        // Failed to send data
        println("Failed to send data to Wearable: $it")
    }
}