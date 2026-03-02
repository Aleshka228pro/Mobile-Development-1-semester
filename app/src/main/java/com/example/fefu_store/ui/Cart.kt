package com.example.fefu_store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fefu_store.MainViewModel
import com.example.fefu_store.model.CartItem
import com.example.fefu_store.ui.theme.DarkGray
import com.example.fefu_store.ui.theme.LightGray
import com.example.fefu_store.ui.theme.White
import com.example.fefu_store.utils.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: MainViewModel) {
    val cartItems = viewModel.cartItems
    val total = viewModel.totalPrice()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = White,
        topBar = {
            Surface(
                color = White,
                shadowElevation = 4.dp
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Корзина",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    },
                    actions = {
                        TextButton(onClick = { viewModel.clearCart() }) {
                            Text("Очистить", color = LightGray)
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
            ) {
                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Корзина пуста", color = Color(0xFF8E8E8E))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 140.dp, start = 16.dp, end = 16.dp)
                    ) {
                        items(
                            items = cartItems,
                            key = { item -> item.product.id to item.size }
                        ) { item ->
                            CartItemRow(
                                item = item,
                                onPlus = { viewModel.addToCart(item.product, item.size) },
                                onMinus = { viewModel.removeFromCart(item.product, item.size) }
                            )
                            Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 45.dp)
            ) {
                BottomSummaryBar(
                    total = total,
                    isEmpty = cartItems.isEmpty(),
                    onCheckout = {
                        if (cartItems.isNotEmpty()) {
                            showDialog = true
                        }
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Заказ оформлен") },
            text = { Text("Ваш заказ успешно оформлен!") },
            confirmButton = {
                Button(onClick = {
                    viewModel.clearCart()
                    showDialog = false
                }) {
                    Text("Вернуться в меню")
                }
            }
        )
    }
}

@Composable
private fun BottomSummaryBar(
    total: Int,
    isEmpty: Boolean,
    onCheckout: () -> Unit
) {
    Surface(
        color = White,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Итого",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${formatPrice(total)} ₽",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEmpty) Color(0xFFCCCCCC) else DarkGray
                ),
                enabled = !isEmpty
            ) {
                Text(
                    text = "Перейти к оформлению",
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.product.title,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Размер: ${item.size}",
                color = LightGray,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${item.product.price} ₽",
                color = LightGray,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "−",
                modifier = Modifier
                    .clickable { onMinus() }
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )

            Text(
                text = " ${item.quantity} ",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Text(
                text = "+",
                modifier = Modifier
                    .clickable { onPlus() }
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }
    }
}
