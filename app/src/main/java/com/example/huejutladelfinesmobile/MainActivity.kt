package com.example.huejutladelfinesmobile

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huejutladelfinesmobile.model.NotificationModel
import com.example.huejutladelfinesmobile.service.NotificationApiService
import com.example.huejutladelfinesmobile.ui.theme.HomeScreen
import com.example.huejutladelfinesmobile.ui.theme.HuejutladelfinesmobileTheme
import com.example.huejutladelfinesmobile.ui.theme.LoginScreen
import com.example.huejutladelfinesmobile.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val URL_BASE = "https://huejutla-delfines-mern-backend.vercel.app/api/"
    private val CHANNEL_ID = "notification_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.INTERNET)
            }
        }

        setContent {
            HuejutladelfinesmobileTheme {
                val navController = rememberNavController()
                val notificationList = remember { mutableStateListOf<NotificationModel>() }
                val previousSize = remember { mutableStateOf(0) }

                Surface(

                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController = navController, viewModel = loginViewModel)
                        }
                        composable("home") {
                            HomeScreen()
                        }
                    }

                    LaunchedEffect(Unit) {
                        val retrofit = Retrofit.Builder()
                            .baseUrl(URL_BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val service = retrofit.create(NotificationApiService::class.java)

                        while (true) {
                            try {
                                val response = service.getNotifications()
                                notificationList.clear()
                                notificationList.addAll(response)
                                response.forEach { notification ->
                                    Log.d("API_RESPONSE", notification.toString())
                                }
                                if (notificationList.size > previousSize.value) {
                                    sendNotification(notificationList.last())
                                    previousSize.value = notificationList.size
                                    println("hola pase por aqui")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            delay(10000) // Verificar cada 10 segundos
                        }
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Manejar permiso denegado
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Channel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(notification: NotificationModel) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                // Reemplaza con tu ícono de notificación
                .setSmallIcon(R.drawable.ic_logo_hd_background)
                .setContentTitle(notification.title)
                .setContentText(notification.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        } else {
            println("Permiso de notificaciones no concedido")
        }
    }
}