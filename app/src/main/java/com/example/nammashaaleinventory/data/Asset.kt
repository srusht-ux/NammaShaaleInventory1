package com.example.nammashaaleinventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val serialNumber: String,
    val condition: String, // Working, Broken, Needs Repair, Missing
    val issueDescription: String = "",
    val repairStatus: String = "Normal" // Normal, In Repair, Repaired
)
