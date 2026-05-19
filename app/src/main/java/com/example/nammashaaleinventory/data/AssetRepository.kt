package com.example.nammashaaleinventory.data

import kotlinx.coroutines.flow.Flow

class AssetRepository(private val assetDao: AssetDao) {
    val allAssets: Flow<List<AssetEntity>> = assetDao.getAllAssets()
    val totalCount: Flow<Int> = assetDao.getTotalCount()
    val workingCount: Flow<Int> = assetDao.getWorkingCount()
    val brokenCount: Flow<Int> = assetDao.getBrokenCount()
    val needsRepairCount: Flow<Int> = assetDao.getNeedsRepairCount()
    val repairRequests: Flow<List<AssetEntity>> = assetDao.getRepairRequests()

    suspend fun insert(asset: AssetEntity) {
        assetDao.insertAsset(asset)
    }

    suspend fun update(asset: AssetEntity) {
        assetDao.updateAsset(asset)
    }

    suspend fun delete(asset: AssetEntity) {
        assetDao.deleteAsset(asset)
    }

    suspend fun getAssetById(id: Int): AssetEntity? {
        return assetDao.getAssetById(id)
    }
}
