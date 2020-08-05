package com.abhishekn.techm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishekn.techm.model.Row

@Dao
interface ImageDao {

    // Insert Data into Database Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageTable: List<Row?>)

    // Retrieve All data from DB
    @Query("SELECT * FROM image_table")
    fun getAllImages() : LiveData<List<Row>>

    // Delete all data from DB
    @Query("DELETE FROM image_table")
    suspend fun deleteAll()
}
