package com.example.data.model

data class StudyChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val topicId: String,
    val topicTitle: String,
    val userQuestion: String,
    val aiResponse: String? = null,
    val isLoading: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
