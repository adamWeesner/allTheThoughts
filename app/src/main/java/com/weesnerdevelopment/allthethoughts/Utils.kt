package com.weesnerdevelopment.allthethoughts

import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(date: Long?, pattern: String? = null): String? {
    val defaultPattern = "MMM dd, yyyy h:mm:ss a"
    val simpleDateFormat = SimpleDateFormat(pattern ?: defaultPattern, Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun formatDate(date: Long?) = formatDateTime(date, "MMM dd, yyyy")
fun formatTime(date: Long?) = formatDateTime(date, "h:mm:ss a")
