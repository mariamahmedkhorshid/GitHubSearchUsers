package com.example.gitsearch.model

data class PersonalInfo(
    val followers: Int,
    val following: Int,
    val company: String,
    val name: String,
    val location: String,
    val email: String,
    val bio: String,
    val created_at: String,
    val repos_url: String,
    val avatar_url: String,
    val public_repos: Int


)
