package com.example.huejutladelfinesmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huejutladelfinesmobile.ui.theme.HomeScreen
import com.example.huejutladelfinesmobile.ui.theme.HuejutladelfinesmobileTheme
import com.example.huejutladelfinesmobile.ui.theme.LoginScreen
import com.example.huejutladelfinesmobile.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuejutladelfinesmobileTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    //Centra el contenido vertical y horizontalmente en la pantalla completa
                    modifier = Modifier.fillMaxSize(),

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
                }
            }
        }
    }
}
