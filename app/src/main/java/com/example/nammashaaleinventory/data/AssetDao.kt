package com.example.nammashaaleinventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets ORDER BY name ASC")
    fun getAllAssets(): Flow<List<AssetEntity>>

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getAssetById(id: Int): AssetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity)

    @Update
    suspend fun updateAsset(asset: AssetEntity)

    @Delete
    suspend fun deleteAsset(asset: AssetEntity)

    @Query("SELECT COUNT(*) FROM assets")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = 'Working'")
    fun getWorkingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = 'Broken'")
    fun getBrokenCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = 'Needs Repair'")
    fun getNeedsRepairCount(): Flow<Int>

    @Query("SELECT * FROM assets WHERE condition IN ('Broken', 'Needs Repair')")
    fun getRepairRequests(): Flow<List<AssetEntity>>
}
