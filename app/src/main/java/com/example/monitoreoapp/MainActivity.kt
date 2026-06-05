package com.example.monitoreoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.monitoreoapp.ui.screens.BillingScreen
import com.example.monitoreoapp.ui.screens.ForgotScreen
import com.example.monitoreoapp.ui.screens.LoginScreen
import com.example.monitoreoapp.ui.screens.MapScreen
import com.example.monitoreoapp.ui.screens.NotifyScreen
import com.example.monitoreoapp.ui.screens.ProfileScreen
import com.example.monitoreoapp.ui.theme.MonitoreoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonitoreoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {

                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("mapa") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToForgot = {
                                    navController.navigate("recuperar")
                                }
                            )
                        }

                        composable("recuperar") {
                            ForgotScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("mapa") {
                            MapScreen(
                                onNavigateToBilling = {
                                    navController.navigate("facturas") { popUpTo(0) }
                                },
                                onNavigateToProfile = {
                                    navController.navigate("perfil") { popUpTo(0) }
                                },
                                onNavigateToNotify = {
                                    navController.navigate("notificaciones") { popUpTo(0) }
                                },
                                onLogout = {
                                    navController.navigate("login") { popUpTo(0) }
                                }
                            )
                        }

                        composable("facturas") {
                            BillingScreen(
                                onNavigateToMap = {
                                    navController.navigate("mapa") { popUpTo(0) }
                                },
                                onNavigateToProfile = {
                                    navController.navigate("perfil") { popUpTo(0) }
                                },
                                onNavigateToNotify = {
                                    navController.navigate("notificaciones") { popUpTo(0) }
                                },
                                onLogout = {
                                    navController.navigate("login") { popUpTo(0) }
                                }
                            )
                        }

                        composable("perfil") {
                            ProfileScreen(
                                onNavigateToMap = {
                                    navController.navigate("mapa") { popUpTo(0) }
                                },
                                onNavigateToBilling = {
                                    navController.navigate("facturas") { popUpTo(0) }
                                },
                                onNavigateToNotify = {
                                    navController.navigate("notificaciones") { popUpTo(0) }
                                },
                                onLogout = {
                                    navController.navigate("login") { popUpTo(0) }
                                }
                            )
                        }

                        composable("notificaciones") {
                            NotifyScreen(
                                onNavigateToMap = {
                                    navController.navigate("mapa") { popUpTo(0) }
                                },
                                onNavigateToBilling = {
                                    navController.navigate("facturas") { popUpTo(0) }
                                },
                                onNavigateToProfile = {
                                    navController.navigate("perfil") { popUpTo(0) }
                                },
                                onLogout = {
                                    navController.navigate("login") { popUpTo(0) }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}