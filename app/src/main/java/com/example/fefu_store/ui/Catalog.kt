package com.example.fefu_store.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fefu_store.MainViewModel
import com.example.fefu_store.model.Product
import com.example.fefu_store.ui.theme.DarkGray
import com.example.fefu_store.ui.theme.LightGray
import com.example.fefu_store.ui.theme.White
import com.example.fefu_store.utils.formatPrice

@Composable
fun CatalogScreen(viewModel: MainViewModel) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val categories = listOf("Все", "Футболка", "Обувь")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 16.dp)
    ) {
        CategoryChipsRow(
            categories = categories,
            selected = viewModel.filter,
            onSelect = { viewModel.changeFilter(it) }
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp, start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            items(
                items = viewModel.filteredProducts(),
                key = { product -> product.id }
            ) { product ->
                val countInCart = viewModel.cartItems.filter { it.product.id == product.id }.sumOf { it.quantity }
                
                ProductRow(
                    product = product,
                    countInCart = countInCart,
                    onAdd = { selectedProduct = product },
                    onRemove = { 
                        val firstSize = viewModel.cartItems.find { it.product.id == product.id }?.size
                        if (firstSize != null) {
                            viewModel.removeFromCart(product, firstSize)
                        }
                    },
                    onClick = { selectedProduct = product }
                )
            }
        }
    }

    selectedProduct?.let { product ->
        ProductDialog(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = { size ->
                viewModel.addToCart(product, size)
                selectedProduct = null
            }
        )
    }
}

@Composable
private fun CategoryChipsRow(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEach { title ->
            val isSelected = title == selected

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (isSelected) DarkGray else Color(0xFFF2F2F2),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(34.dp),
                onClick = { onSelect(title) }
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) White else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductRow(
    product: Product,
    countInCart: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductImage(product = product)

        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = product.description,
                color = LightGray,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )

            Spacer(Modifier.height(12.dp))

            if (countInCart == 0) {
                PricePill(price = product.price, onClick = onAdd)
            } else {
                CounterPill(
                    count = countInCart,
                    onMinus = onRemove,
                    onPlus = onAdd
                )
            }
        }
    }
}

@Composable
private fun ProductImage(product: Product) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.title,
            modifier = Modifier.size(110.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun PricePill(
    price: Int,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF0F0F0)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${formatPrice(price)} ₽",
                color = DarkGray,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CounterPill(
    count: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFE0E0E0)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
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
                text = " $count ",
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
