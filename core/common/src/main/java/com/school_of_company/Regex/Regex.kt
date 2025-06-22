package com.school_of_company.Regex

import kotlin.text.Regex

object Regex {
    const val Id =  "^[a-zA-Z]+$"
    const val PASSWORD = "^(?=.*[a-zA-Z])(?=.*[!@#\\\$%^*+=-?<>])(?=.*[0-9]).{6,}\$"
}

fun isValidPassword(password: String): Boolean {
    return com.school_of_company.Regex.Regex.PASSWORD.toRegex().matches(password)
}
fun isValidId(id: String): Boolean {
    return com.school_of_company.Regex.Regex.Id.toRegex().matches(id)
}