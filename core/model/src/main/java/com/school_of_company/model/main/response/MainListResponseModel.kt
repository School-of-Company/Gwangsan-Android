package com.school_of_company.model.main.response

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type

data class MainListResponseModel(
    val id: Number,
    val content: String,
    val gwangsan: Number,
    val type: Type,
    val mode: Mode,
    val title: String,
    val imageUrls: String
)