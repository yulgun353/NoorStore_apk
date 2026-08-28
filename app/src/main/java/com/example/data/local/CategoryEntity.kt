package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val nameUg: String,
    val nameAr: String,
    val nameEn: String,
    val iconName: String
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerName: String,
    val customerPhone: String,
    val orderSummary: String,
    val totalAmount: Double,
    val orderDate: Long = System.currentTimeMillis(),
    val note: String = "",
    val status: String = "Pending"
)
