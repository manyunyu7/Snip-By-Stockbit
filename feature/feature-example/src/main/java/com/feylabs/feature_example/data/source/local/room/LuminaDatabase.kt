package com.feylabs.feature_example.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.feylabs.feature_example.data.source.local.dao.LuminaDAO
import com.feylabs.feature_example.data.source.local.entity.LuminaItemEntity

@Database(
    entities = [
        LuminaItemEntity::class], version = 168
)
abstract class LuminaDatabase : RoomDatabase() {
    abstract fun luminaDao(): LuminaDAO
}