package com.example.nammashaaleinventory.ui

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object AllAssets : Screen("all_assets")
    object AddAsset : Screen("add_asset")
    object RepairRequests : Screen("repair_requests")
    object Profile : Screen("profile")
    object AssetDetails : Screen("asset_details/{assetId}") {
        fun createRoute(assetId: Int) = "asset_details/$assetId"
    }
}
