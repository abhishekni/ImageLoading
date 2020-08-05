package com.abhishekn.techm.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image_table", indices = arrayOf(Index(value = ["title", "description", "imageHref"], unique = true)))
data class Row(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String?,
    val description: String?,
    val imageHref: String?
)