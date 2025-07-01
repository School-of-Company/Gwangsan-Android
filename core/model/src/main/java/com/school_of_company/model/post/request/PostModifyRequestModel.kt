package com.school_of_company.model.post.request

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type

data class PostModifyRequestModel (
    val type: Type,
    val mode: Mode,
    val title: String,
    val content: String,
    val gwangsan: Number,
    val imageIds: List<Number>
)