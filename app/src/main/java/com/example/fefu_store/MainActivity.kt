package com.example.fefu_store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fefu_store.ui.CartScreen
import com.example.fefu_store.ui.CatalogScreen
import com.example.fefu_store.ui.theme.DarkGray
import com.example.fefu_store.ui.theme.FEFU_StoreTheme
import com.example.fefu_store.ui.theme.LightGray
import com.example.fefu_store.ui.theme.White

class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FEFU_StoreTheme {
                App(viewModel)
            }
        }
    }
}

@Composable
fun App(viewModel: MainViewModel) {
    var currentRoute by remember { mutableStateOf("catalog") }

    Scaffold(
        containerColor = White,
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            Surface(
                color = White,
                shadowElevation = 8.dp,
                modifier = Modifier.systemBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Меню",
                        fontSize = 18.sp,
                        fontWeight = if (currentRoute == "catalog") FontWeight.Bold else FontWeight.Normal,
                        color = if (currentRoute == "catalog") DarkGray else LightGray,
                        modifier = Modifier.clickable { currentRoute = "catalog" }
                    )
                    Text(
                        text = "Корзина",
                        fontSize = 18.sp,
                        fontWeight = if (currentRoute == "cart") FontWeight.Bold else FontWeight.Normal,
                        color = if (currentRoute == "cart") DarkGray else LightGray,
                        modifier = Modifier.clickable { currentRoute = "cart" }
                    )
                }
            }
        }
    ) { _ ->
        when (currentRoute) {
            "catalog" -> CatalogScreen(viewModel)
            "cart" -> CartScreen(viewModel)
        }
    }
}
