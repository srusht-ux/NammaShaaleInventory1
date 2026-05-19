package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammashaaleinventory.data.AssetEntity
import com.example.nammashaaleinventory.data.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAssetsScreen(
    viewModel: AssetViewModel,
    onBack: () -> Unit,
    onAssetClick: (Int) -> Unit
) {
    val assets by viewModel.allAssets.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredAssets = assets.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.serialNumber.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color(0xFF2F64E1))) {
                TopAppBar(
                    title = { Text("All Assets", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2F64E1))
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by name or serial...", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                    ),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color(0xFFF5F7FA))) {
            if (filteredAssets.isEmpty()) {
                EmptyStateUI(searchQuery)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredAssets) { asset ->
                        AssetCard(asset, onAssetClick, { viewModel.delete(asset) })
                    }
                }
            }
        }
    }
}

@Composable
fun AssetCard(asset: AssetEntity, onClick: (Int) -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(asset.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(50.dp).background(getConditionColor(asset.condition).copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Inventory2, contentDescription = null, tint = getConditionColor(asset.condition))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = asset.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "SN: ${asset.serialNumber}", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                ConditionBadge(asset.condition)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.6f))
            }
        }
    }
}

@Composable
fun ConditionBadge(condition: String) {
    Surface(
        color = getConditionColor(condition).copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = condition,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = getConditionColor(condition)
        )
    }
}

fun getConditionColor(condition: String): Color {
    return when (condition) {
        "Working" -> Color(0xFF4CAF50)
        "Needs Repair" -> Color(0xFFFF9800)
        "Broken" -> Color(0xFFF44336)
        "Missing" -> Color(0xFF607D8B)
        else -> Color.Gray
    }
}

@Composable
fun EmptyStateUI(query: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (query.isEmpty()) "No assets found" else "No matches for \"$query\"",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}
