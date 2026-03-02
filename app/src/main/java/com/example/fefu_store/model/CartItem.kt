package com.example.fefu_store.model

data class CartItem(
    val product: Product,
    val size: String,
    val quantity: Int = 1
)
