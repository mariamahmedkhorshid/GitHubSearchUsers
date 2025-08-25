package com.example.gitsearch.model

data class UserResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserData>
)
