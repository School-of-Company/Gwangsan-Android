package com.school_of_company.inform.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
fun formatIsoWithoutTimezone(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return ""
    return try {
        val parsed = LocalDateTime.parse(isoDate)
        parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd / HH:mm"))
    } catch (e: Exception) {
        Log.w("DateFormatter", "Failed to parse date: $isoDate", e)
        ""
    }
}