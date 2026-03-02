package com.example.fefu_store.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fefu_store.model.Product
import com.example.fefu_store.ui.theme.DarkGray
import com.example.fefu_store.ui.theme.LightGray
import com.example.fefu_store.ui.theme.White

@Composable
fun ProductDialog(
    product: Product,
    onDismiss: () -> Unit,
    onAddToCart: (String) -> Unit
) {
    val sizes = listOf("S", "M", "L", "XL")
    var selectedSize by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = product.description,
                    color = LightGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Выберите размер:",
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sizes.forEach { size ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedSize == size)
                                DarkGray
                            else
                                Color(0xFFF0F0F0),
                            modifier = Modifier
                                .clickable { selectedSize = size }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = size,
                                color = if (selectedSize == size)
                                    White
                                else
                                    DarkGray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontWeight = if (selectedSize == size)
                                    FontWeight.Bold
                                else
                                    FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Цена: ${formatPrice(product.price)} ₽",
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGray
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { selectedSize?.let { onAddToCart(it) } },
                enabled = selectedSize != null,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGray
                )
            ) {
                Text(
                    text = "В корзину",
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Отмена")
            }
        },
        containerColor = White,
        titleContentColor = Color.Black,
        textContentColor = LightGray
    )
}

private fun formatPrice(value: Int): String {
    val s = value.toString()
    val sb = StringBuilder()
    for (i in s.indices) {
        sb.append(s[i])
        val left = s.length - 1 - i
        if (left > 0 && left % 3 == 0) sb.append(' ')
    }
    return sb.toString()
}
