package com.school_of_company.model.auth.request

data class SignUpRequestModel(
    val name: String,
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val dongName: String,
    val placeName: String,
    val recommender: String,
    val specialties: List<String>,
    val description: String
)