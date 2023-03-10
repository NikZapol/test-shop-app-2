package com.example.home.models

data class Sale(
    val category: String,
    val discount: Int,
    val image_url: String,
    val name: String,
    val price: Double
)