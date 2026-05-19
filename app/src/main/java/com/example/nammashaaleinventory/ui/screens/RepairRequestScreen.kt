package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun RepairRequestScreen(
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    val repairAssets by viewModel.repairRequests.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repair Requests", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2F64E1))
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color(0xFFF5F7FA))) {
            if (repairAssets.isEmpty()) {
                EmptyRepairStateUI()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(repairAssets) { asset ->
                        RepairCard(asset) {
                            viewModel.update(asset.copy(condition = "Working", repairStatus = "Repaired", issueDescription = ""))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepairCard(asset: AssetEntity, onMarkRepaired: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Engineering, contentDescription = null, tint = Color(0xFFFF9800))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = asset.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Issue: ${asset.issueDescription}", fontSize = 14.sp, color = Color.DarkGray)
            Text(text = "Condition: ${asset.condition}", fontSize = 14.sp, color = Color.Red)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onMarkRepaired,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("MARK AS REPAIRED")
            }
        }
    }
}

@Composable
fun EmptyRepairStateUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Engineering, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "All systems operational!", fontSize = 18.sp, color = Color.Gray)
        Text(text = "No pending repair requests", fontSize = 14.sp, color = Color.LightGray)
    }
}
