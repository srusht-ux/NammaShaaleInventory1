package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammashaaleinventory.data.AssetViewModel

@Composable
fun DashboardScreen(
    viewModel: AssetViewModel,
    onNavigate: (String) -> Unit
) {
    val totalCount by viewModel.totalCount.collectAsState(initial = 0)
    val workingCount by viewModel.workingCount.collectAsState(initial = 0)
    val brokenCount by viewModel.brokenCount.collectAsState(initial = 0)
    val needsRepairCount by viewModel.needsRepairCount.collectAsState(initial = 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2F64E1))
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Dashboard",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "NammaShaale Inventory",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
                IconButton(onClick = { onNavigate("profile") }) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Total Assets", totalCount.toString(), Icons.Default.Inventory, Color(0xFF2F64E1), Modifier.weight(1f))
                StatCard("Working", workingCount.toString(), Icons.Default.CheckCircle, Color(0xFF4CAF50), Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard("Needs Repair", needsRepairCount.toString(), Icons.Default.Build, Color(0xFFFF9800), Modifier.weight(1f))
                StatCard("Broken", brokenCount.toString(), Icons.Default.Cancel, Color(0xFFF44336), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Quick Actions", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton("View All", Icons.Default.List, { onNavigate("all_assets") }, Modifier.weight(1f))
                ActionButton("Add Asset", Icons.Default.Add, { onNavigate("add_asset") }, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton("Repair Req.", Icons.Default.Engineering, { onNavigate("repair_requests") }, Modifier.weight(1f))
                ActionButton("Reports", Icons.Default.Assessment, { }, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Recent Activities", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
            Spacer(modifier = Modifier.height(12.dp))
            ActivityItem("Microscope updated", "10 mins ago")
            ActivityItem("New Football added", "2 hours ago")
            ActivityItem("Projector repair requested", "1 day ago")
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Column {
                Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = title, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ActionButton(label: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF2F64E1)),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ActivityItem(title: String, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(Color(0xFFE3F2FD), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = Color(0xFF2F64E1), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = time, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
