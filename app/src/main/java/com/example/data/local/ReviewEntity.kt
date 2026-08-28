package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val userName: String,
    val comment: String,
    val rating: Int = 5,
    val timestamp: Long = System.currentTimeMillis(),
    val adminReply: String = ""
)
