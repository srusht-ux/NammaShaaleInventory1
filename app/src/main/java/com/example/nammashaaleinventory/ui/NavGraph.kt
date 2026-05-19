package com.example.nammashaaleinventory.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nammashaaleinventory.data.AssetViewModel
import com.example.nammashaaleinventory.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: AssetViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNext = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel,
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
        
        composable(Screen.AllAssets.route) {
            AllAssetsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onAssetClick = { assetId ->
                    navController.navigate(Screen.AssetDetails.createRoute(assetId))
                }
            )
        }
        
        composable(Screen.AddAsset.route) {
            AddAssetScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.AssetDetails.route,
            arguments = listOf(navArgument("assetId") { type = NavType.IntType })
        ) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getInt("assetId") ?: return@composable
            AssetDetailsScreen(
                assetId = assetId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.RepairRequests.route) {
            RepairRequestScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
