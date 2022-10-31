package com.example.githubapi.models

import java.sql.Timestamp

data class PrListItem(
    val url: String?,
    val id: Long,
    val state: String?,
    val user: User?,
    val title: String?,
    val created_at: Timestamp?,
    val closed_at: Timestamp?,
    val html_url: String?,
)

data class User(
    val avatar_url: String?,
    val login: String?,
)