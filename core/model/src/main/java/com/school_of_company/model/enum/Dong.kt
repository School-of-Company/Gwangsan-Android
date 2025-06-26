package com.school_of_company.model.enum

enum class Dong {
    ROLE_DONG_1,
    ROLE_DONG_2,
    ROLE_DONG_3,
    ROLE_DONG_4,
    ROLE_DONG_5;

    companion object {
        fun from(value: String): Dong = entries.firstOrNull { it.name == value }
            ?: ROLE_DONG_1
    }
}