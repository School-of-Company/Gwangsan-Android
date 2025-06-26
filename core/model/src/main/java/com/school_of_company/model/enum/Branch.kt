package com.school_of_company.model.enum

enum class Branch {
    ROLE_STUDENT,
    ROLE_STUDENT_COUNCIL;

    companion object {
        fun from(value: String): Branch = entries.firstOrNull { it.name == value }
            ?: ROLE_STUDENT
    }
}