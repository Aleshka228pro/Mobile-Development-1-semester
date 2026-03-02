package com.example.fefu_store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.fefu_store.data.products
import com.example.fefu_store.model.CartItem
import com.example.fefu_store.model.Product

class MainViewModel {

    var filter by mutableStateOf("Все")
        private set

    val allProducts: List<Product> = products
    val cartItems = mutableStateListOf<CartItem>()

    fun changeFilter(category: String) {
        filter = category
    }

    fun addToCart(product: Product, size: String) {
        val existingIndex = cartItems.indexOfFirst { it.product.id == product.id && it.size == size }
        if (existingIndex != -1) {
            val existing = cartItems[existingIndex]
            cartItems[existingIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            cartItems.add(CartItem(product = product, size = size, quantity = 1))
        }
    }

    fun removeFromCart(product: Product, size: String) {
        val existingIndex = cartItems.indexOfFirst { it.product.id == product.id && it.size == size }
        if (existingIndex != -1) {
            val existing = cartItems[existingIndex]
            if (existing.quantity > 1) {
                cartItems[existingIndex] = existing.copy(quantity = existing.quantity - 1)
            } else {
                cartItems.removeAt(existingIndex)
            }
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun totalPrice(): Int = cartItems.sumOf { it.product.price * it.quantity }
    
    fun filteredProducts(): List<Product> = allProducts.filter { filter == "Все" || it.category == filter }
}
