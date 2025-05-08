package com.example.interviewprep.data.model

import java.util.Date

data class InterviewSession(
    val id: String = "",
    val userId: String = "",
    val jobDescription: String = "",
    val resumeUrl: String = "",
    val status: InterviewStatus = InterviewStatus.IN_PROGRESS,
    val score: Int = 0,
    val feedback: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class InterviewStatus {
    IN_PROGRESS,
    COMPLETED,
    ABANDONED
} 