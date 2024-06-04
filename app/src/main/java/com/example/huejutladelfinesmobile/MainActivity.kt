package com.example.huejutladelfinesmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.huejutladelfinesmobile.ui.theme.HuejutladelfinesmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuejutladelfinesmobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //Centra el contenido vertical y horizontalmente en la pantalla completa
                    modifier = Modifier.fillMaxSize(),

                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

// Login Screen input email and password
@Preview
@Composable
fun LoginScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        //mensaje de bienvenida login screen
        Text("Bienvenido a Huejutla Delfines",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = MaterialTheme.typography.h6.fontWeight,
            textAlign = TextAlign.Center,
        )

        //imagen de login screen url externa a la app


        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Button(onClick = {
            println("Email: $email")
        }, modifier = Modifier.padding(16.dp)
        ) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.button)
        }
    }
}