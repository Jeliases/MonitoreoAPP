package com.example.monitoreoapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoreoapp.R
import com.example.monitoreoapp.ui.viewmodels.ForgotState
import com.example.monitoreoapp.ui.viewmodels.ForgotViewModel

@Composable
fun ForgotScreen(
    viewModel: ForgotViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val forgotState by viewModel.forgotState.collectAsState()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    LaunchedEffect(forgotState) {
        when (forgotState) {
            is ForgotState.Success -> {
                // Muestra el mensaje del servidor ("La solicitud fue enviada con éxito")
                Toast.makeText(context, (forgotState as ForgotState.Success).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
                onNavigateBack() // Devuelve al usuario al Login
            }
            is ForgotState.Error -> {
                Toast.makeText(context, (forgotState as ForgotState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icono),
            contentDescription = "Logo Colibrí",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = "Ingresa tu usuario y te enviaremos un correo electrónico para restablecer tu contraseña.",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Usuario", color = MaterialTheme.colorScheme.secondary) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ícono de Usuario",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { viewModel.recoverPassword(email) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            enabled = forgotState !is ForgotState.Loading
        ) {
            if (forgotState is ForgotState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Enviar", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = onNavigateBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text("Cancelar", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}