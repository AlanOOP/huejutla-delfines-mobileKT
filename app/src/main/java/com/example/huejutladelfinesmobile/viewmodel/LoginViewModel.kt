package com.example.huejutladelfinesmobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.huejutladelfinesmobile.datastore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import kotlin.math.log

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val context = application.applicationContext

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val client = OkHttpClient()
            val requestBody = JSONObject()
                .put("email", email.value)
                .put("password", password.value)
                .toString()
                .toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url("https://huejutla-delfines-mern-backend.vercel.app/api/singUp")
                .post(requestBody)
                .build()


            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }



                if (response.isSuccessful){
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        println(responseBody)

                        val jsonResponse = JSONObject(responseBody)
                        println(jsonResponse)
                        val userObject = jsonResponse.getJSONObject("user")
                        val sessionToken = userObject.getString("token")
                        println(sessionToken)
                        saveSessionToken(sessionToken)
                        _isAuthenticated.value = true
                        onSuccess()
                    } else {
                        onError()
                    }
                } else {
                    onError()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                onError()
            }
        }
    }

    private fun saveSessionToken(token: String) {
        viewModelScope.launch {
            context.datastore.edit { preferences ->
                preferences[SESSION_TOKEN_KEY] = token
            }
        }
    }

    companion object {
        private val SESSION_TOKEN_KEY = stringPreferencesKey("session_token")
    }
}