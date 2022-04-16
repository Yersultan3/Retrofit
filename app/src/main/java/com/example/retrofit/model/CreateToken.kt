package com.example.retrofit.model

data class CreateToken(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)
