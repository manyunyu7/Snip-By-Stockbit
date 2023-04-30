package com.feylabs.feature_example.data.source.local.dao

import androidx.room.*
import com.feylabs.feature_example.data.source.local.entity.LuminaItemEntity

@Dao
interface LuminaDAO {

    @Query("SELECT * FROM lumina_items  ORDER BY id DESC")
    fun getAll(): List<LuminaItemEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<LuminaItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LuminaItemEntity)

    @Delete
    fun delete(quotes: LuminaItemEntity)

    @Query("DELETE FROM lumina_items where id <> 0")
    fun deleteAll()
}