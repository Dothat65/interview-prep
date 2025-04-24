package com.example.interviewprep.data.model

import java.util.Date

data class ChatMessage(
    val id: String = "",
    val sessionId: String = "",
    val content: String = "",
    val sender: MessageSender = MessageSender.AI,
    val timestamp: Date = Date(),
    val feedback: String? = null
)

enum class MessageSender {
    USER,
    AI
} 