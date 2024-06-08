package com.example.huejutladelfinesmobile

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.datastore by preferencesDataStore("user_preferences")