package com.example.nammashaaleinventory

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Asset(
    val name: String,
    var condition: String,
    var issue: String = ""
)

@Composable
fun NammaShaaleApp() {

    var currentScreen by remember { mutableStateOf("dashboard") }

    var selectedAsset by remember {
        mutableStateOf(
            Asset(
                "Microscope",
                "Working"
            )
        )
    }

    var assetList by remember {
        mutableStateOf(
            mutableListOf(
                Asset("Microscope", "Working"),
                Asset("Football", "Missing"),
                Asset("Projector", "Needs Repair"),
                Asset("Notebook Set", "Working"),
                Asset("Tablet", "Broken")
            )
        )
    }

    when (currentScreen) {

        "dashboard" -> DashboardScreen(
            onViewAssets = {
                currentScreen = "assets"
            }
        )

        "assets" -> AllAssetsScreen(
            assetList = assetList,
            onBack = {
                currentScreen = "dashboard"
            },
            onAssetClick = {
                selectedAsset = it
                currentScreen = "details"
            }
        )

        "details" -> AssetDetailsScreen(
            asset = selectedAsset,
            onBack = {
                currentScreen = "assets"
            }
        )
    }
}

@Composable
fun DashboardScreen(
    onViewAssets: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEAEA))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2F64E1))
                .padding(20.dp)
        ) {

            Text(
                text = "Dashboard",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            DashboardCard("Total Assets", "152", Color.Blue)

            DashboardCard("Working", "98", Color.Green)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            DashboardCard("Needs Repair", "32", Color(0xFFFF9800))

            DashboardCard("Broken", "22", Color.Red)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Recent Activities",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        ActivityCard("Microscope marked as needs repair", "1 hour ago")
        ActivityCard("Football reported missing", "2 hours ago")
        ActivityCard("Projector repair request sent", "1 day ago")

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onViewAssets,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F64E1)
            )
        ) {

            Text(
                "VIEW ALL ASSETS",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    number: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp),
        shape = RoundedCornerShape(25.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = number,
                fontSize = 40.sp,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ActivityCard(
    title: String,
    time: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = time,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AllAssetsScreen(
    assetList: List<Asset>,
    onBack: () -> Unit,
    onAssetClick: (Asset) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEAEA))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2F64E1))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A4FB3)
                )
            ) {

                Text("Back")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "All Assets",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(assetList) { asset ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            onAssetClick(asset)
                        },
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Text(
                        text = "${asset.name} - ${asset.condition}",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AssetDetailsScreen(
    asset: Asset,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    var selectedCondition by remember {
        mutableStateOf(asset.condition)
    }

    var issueText by remember {
        mutableStateOf(asset.issue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2F64E1))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onBack
            ) {

                Text("Back")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Asset Details",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = asset.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Current Condition: $selectedCondition",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Update Condition",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row {

                Button(
                    onClick = {
                        selectedCondition = "Working"
                        asset.condition = "Working"

                        Toast.makeText(
                            context,
                            "Condition Updated to Working",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green
                    )
                ) {

                    Text("Working")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        selectedCondition = "Needs Repair"
                        asset.condition = "Needs Repair"

                        Toast.makeText(
                            context,
                            "Marked as Needs Repair",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {

                    Text("Repair")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        selectedCondition = "Broken"
                        asset.condition = "Broken"

                        Toast.makeText(
                            context,
                            "Marked as Broken",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {

                    Text("Broken")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Report Issue",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = issueText,
                onValueChange = {
                    issueText = it
                },
                label = {
                    Text("Enter Issue")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    asset.issue = issueText

                    Toast.makeText(
                        context,
                        "Issue Reported Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {

                Text("REPORT ISSUE")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Reported Issue:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (asset.issue.isEmpty())
                    "No issues reported"
                else
                    asset.issue,
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
    }
}