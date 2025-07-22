package com.school_of_company.chat.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val CHAT_TIME_FORMATTER = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA)
private val CHAT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분", Locale.KOREA)

fun formatChatTimeToDate(rawTime: String): String {
    return try {
        val parsed = LocalDateTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        parsed.format(CHAT_DATE_FORMATTER)
    } catch (e: Exception) {
        rawTime
    }
}

fun formatChatTime(rawTime: String): String {
    return try {
        val parsed = LocalDateTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        parsed.format(CHAT_TIME_FORMATTER)
    } catch (e: Exception) {
        rawTime
    }
}
