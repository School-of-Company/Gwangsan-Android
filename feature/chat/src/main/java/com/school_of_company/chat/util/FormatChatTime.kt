package com.school_of_company.chat.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatChatTimeToDate(rawTime: String): String {
    val parsed = LocalDateTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분", Locale.KOREA)
    return parsed.format(outputFormatter)
}

fun formatChatTime(rawTime: String): String {
    val parsed = LocalDateTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    val outputFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA)
    return parsed.format(outputFormatter)
}
