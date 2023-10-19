package com.example.crypto.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crypto.ui.screens.sendeth.SendEthScreen

object Routes {
    const val SendEth = "sendeth"
}

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SendEth,
    ) {
        composable(Routes.SendEth) {
            SendEthScreen(viewModel = hiltViewModel(), navController = navController)
        }
    }
}