package com.school_of_company.Regex

import kotlin.text.Regex

object Regex {
    const val ID = "^[가-힣]+$"
    const val PASSWORD = "^(?=.*[a-zA-Z])(?=.*[!@#\\\$%^*+=-?<>])(?=.*[0-9]).{6,}\$"
}

fun isValidPassword(password: String): Boolean {
    return com.school_of_company.Regex.Regex.PASSWORD.toRegex().matches(password)
}
fun isValidId(id: String): Boolean {
    return com.school_of_company.Regex.Regex.ID.toRegex().matches(id)
}

fun isValidPhoneNumber(phone: String): Boolean {
    val regex = Regex("^01[016789][0-9]{7,8}$")
    return regex.matches(phone)
}