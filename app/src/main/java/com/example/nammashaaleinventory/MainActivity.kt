package com.example.nammashaaleinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nammashaaleinventory.data.AssetViewModel
import com.example.nammashaaleinventory.ui.NavGraph
import com.example.nammashaaleinventory.ui.theme.NammaShaaleInventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammaShaaleInventoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: AssetViewModel = viewModel()
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
