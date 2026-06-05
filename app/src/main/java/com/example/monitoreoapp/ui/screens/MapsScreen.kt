package com.example.monitoreoapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoreoapp.R
import com.example.monitoreoapp.data.model.Vehicle
import com.example.monitoreoapp.ui.viewmodels.MapState
import com.example.monitoreoapp.ui.viewmodels.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val mapState by viewModel.mapState.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.060, -77.030), 13f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Monitorea tu Envío",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Salir", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary, // El celeste del Figma
                contentColor = Color.White,
                modifier = Modifier.height(60.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.Default.LocalShipping, contentDescription = "Mapa", tint = Color.White)
                    }
                    IconButton(onClick = { }) { Icon(Icons.Default.Receipt, contentDescription = "Facturas", tint = Color.White) }
                    IconButton(onClick = { }) { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White) }
                    IconButton(onClick = { }) { Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = Color.White) }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (mapState) {
                is MapState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MapState.Error -> {
                    Text(
                        text = (mapState as MapState.Error).message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is MapState.Success -> {
                    val vehicles = (mapState as MapState.Success).vehicles

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        vehicles.forEach { vehicle ->
                            MarkerComposable(
                                state = MarkerState(position = LatLng(vehicle.latitude, vehicle.longitude)),
                                anchor = Offset(0.5f, 0.5f) // Centra el diseño exactamente en la coordenada
                            ) {
                                CustomVehicleMarker(vehicle = vehicle)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomVehicleMarker(vehicle: Vehicle) {

    val isGreen = vehicle.status == "green"
    val carImage = if (isGreen) R.drawable.carro_verde else R.drawable.carro_rojo
    val mainColor = if (isGreen) Color(0xFF00C853) else MaterialTheme.colorScheme.error
    val speedColor = if (isGreen) Color(0xFF00C853) else Color.DarkGray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = carImage),
            contentDescription = "Vehículo",
            modifier = Modifier
                .size(60.dp)
                .rotate(vehicle.angle) // Rota mágicamente
        )


        Surface(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, mainColor),
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = vehicle.plate,
                color = mainColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }


        Text(
            text = vehicle.speed,
            color = speedColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}