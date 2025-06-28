package com.school_of_company.model.enum

enum class Specialty {
    ROLE_STUDENT,
    ROLE_STUDENT_COUNCIL;

    companion object {
        fun from(value: String): Specialty = entries.firstOrNull { it.name == value }
            ?: ROLE_STUDENT
    }
}