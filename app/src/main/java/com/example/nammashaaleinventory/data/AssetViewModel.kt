package com.example.nammashaaleinventory.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AssetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AssetRepository
    val allAssets: Flow<List<AssetEntity>>
    val totalCount: Flow<Int>
    val workingCount: Flow<Int>
    val brokenCount: Flow<Int>
    val needsRepairCount: Flow<Int>
    val repairRequests: Flow<List<AssetEntity>>

    init {
        val assetDao = AssetDatabase.getDatabase(application).assetDao()
        repository = AssetRepository(assetDao)
        allAssets = repository.allAssets
        totalCount = repository.totalCount
        workingCount = repository.workingCount
        brokenCount = repository.brokenCount
        needsRepairCount = repository.needsRepairCount
        repairRequests = repository.repairRequests
    }

    fun insert(asset: AssetEntity) = viewModelScope.launch {
        repository.insert(asset)
    }

    fun update(asset: AssetEntity) = viewModelScope.launch {
        repository.update(asset)
    }

    fun delete(asset: AssetEntity) = viewModelScope.launch {
        repository.delete(asset)
    }
}
