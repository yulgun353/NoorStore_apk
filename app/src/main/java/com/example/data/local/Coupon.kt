package com.example.data.local

data class Coupon(
    val code: String,
    val discountPercent: Double = 0.0,
    val discountAmount: Double = 0.0,
    val minSpend: Double = 0.0,
    val descUg: String,
    val descAr: String,
    val descEn: String
)
