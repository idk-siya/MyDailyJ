package com.example.poefn.model

data class JournalEntry(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String?
)
