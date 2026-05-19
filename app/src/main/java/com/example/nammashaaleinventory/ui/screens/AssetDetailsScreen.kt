package com.example.nammashaaleinventory.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammashaaleinventory.data.AssetEntity
import com.example.nammashaaleinventory.data.AssetViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailsScreen(
    assetId: Int,
    viewModel: AssetViewModel,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var asset by remember { mutableStateOf<AssetEntity?>(null) }
    
    var name by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }
    var repairStatus by remember { mutableStateOf("") }
    var expanded = remember { mutableStateOf(false) }
    
    val conditions = listOf("Working", "Broken", "Needs Repair", "Missing")
    val context = LocalContext.current

    LaunchedEffect(assetId) {
        val fetchedAsset = viewModel.allAssets.firstOrNull()?.find { it.id == assetId }
        fetchedAsset?.let {
            asset = it
            name = it.name
            condition = it.condition
            issueDescription = it.issueDescription
            repairStatus = it.repairStatus
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Asset", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2F64E1))
            )
        }
    ) { paddingValues ->
        asset?.let { currentAsset ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Asset: ${currentAsset.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Serial: ${currentAsset.serialNumber}", fontSize = 16.sp, color = Color.Gray)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Current Status", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }
                ) {
                    OutlinedTextField(
                        value = condition,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Condition") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        conditions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    condition = selectionOption
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = issueDescription,
                    onValueChange = { issueDescription = it },
                    label = { Text("Issue Description") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.update(
                            currentAsset.copy(
                                condition = condition,
                                issueDescription = issueDescription
                            )
                        )
                        Toast.makeText(context, "Asset updated successfully", Toast.LENGTH_SHORT).show()
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F64E1))
                ) {
                    Text("UPDATE ASSET", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                
                if (condition == "Broken" || condition == "Needs Repair") {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {
                            viewModel.update(currentAsset.copy(repairStatus = "In Repair"))
                            Toast.makeText(context, "Repair request submitted", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("REQUEST REPAIR", fontSize = 16.sp)
                    }
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        }
    }
}
