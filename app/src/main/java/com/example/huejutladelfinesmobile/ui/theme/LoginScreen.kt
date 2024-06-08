package com.example.huejutladelfinesmobile.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.huejutladelfinesmobile.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Bienvenido a Huejutla Delfines",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = MaterialTheme.typography.h6.fontWeight,
            textAlign = TextAlign.Center,
        )

        Image(
            painter = rememberImagePainter("https://hueutla-delfines.netlify.app/assets/logo-club-7d6cc362.png"),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )

        TextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.onLoginClick(
                    onSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onError = {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.button)
        }
    }
}