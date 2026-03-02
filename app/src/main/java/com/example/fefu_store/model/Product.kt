package com.example.fefu_store.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val imageRes: Int,
    val category: String
)
