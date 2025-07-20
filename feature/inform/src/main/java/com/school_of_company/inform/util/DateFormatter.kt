package com.school_of_company.inform.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatIsoWithoutTimezone(isoDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getDefault()

        val outputFormat = SimpleDateFormat("yyyy.MM.dd / HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()

        val date = inputFormat.parse(isoDate)
        date?.let { outputFormat.format(it) } ?: ""
    } catch (e: Exception) {
        ""
    }
}
