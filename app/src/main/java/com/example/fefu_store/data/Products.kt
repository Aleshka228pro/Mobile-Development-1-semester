package com.example.fefu_store.data

import com.example.fefu_store.R
import com.example.fefu_store.model.Product

val products = listOf(
    Product(
        id = 1,
        title = "Джерси Yeezy",
        description = "Синтетика.",
        price = 4999,
        imageRes = R.drawable.yeezy,
        category = "Футболка"
    ),
    Product(
        id = 2,
        title = "Футболка Bape",
        description = "Хлопок, fw24.",
        price = 3490,
        imageRes = R.drawable.bape,
        category = "Футболка"
    ),
    Product(
        id = 3,
        title = "Maison Mihara Yasuhiro",
        description = "Кеды прямиком из Японии.",
        price = 19900,
        imageRes = R.drawable.mihara,
        category = "Обувь"
    ),
    Product(
        id = 4,
        title = "Кеды Lanvin Curb",
        description = "Кеды от Французкого дома моды.",
        price = 59900,
        imageRes = R.drawable.lanvin,
        category = "Обувь"
    )
)
