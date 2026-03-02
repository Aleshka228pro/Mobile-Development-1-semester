package com.example.fefu_store.utils

fun formatPrice(value: Int): String {
    val s = value.toString()
    val sb = StringBuilder()
    for (i in s.indices) {
        sb.append(s[i])
        val left = s.length - 1 - i
        if (left > 0 && left % 3 == 0) sb.append(' ')
    }
    return sb.toString()
}
