package com.example.interviewprep.data.model

data class FlashCard(
    val id: String = "",
    val question: String = "",
    val answer: String = "",
    val category: String = "",
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val tags: List<String> = emptyList()
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
} 