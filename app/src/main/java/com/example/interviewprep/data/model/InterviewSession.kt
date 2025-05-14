package com.example.interviewprep.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class InterviewSession(
    val id: String = "",
    val userId: String = "",
    val jobDescription: String = "",
    val resumeUrl: String = "",
    val status: InterviewStatus = InterviewStatus.IN_PROGRESS,
    val score: Int = 0,
    val feedback: String = "",
    val questions: List<InterviewQuestion> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class InterviewQuestion(
    val id: String = "",
    val question: String = "",
    val answer: String = "",
    val feedback: String = "",
    val score: Int = 0
)

enum class InterviewStatus {
    IN_PROGRESS,
    COMPLETED,
    ABANDONED
} 