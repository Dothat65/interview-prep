package com.example.interviewprep.ui.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val MOCK_INTERVIEW = "mock_interview"
    const val PRACTICE = "practice"
    const val INTERVIEW_SESSION = "interview_session/{sessionId}"
    
    fun interviewSession(sessionId: String) = "interview_session/$sessionId"
} 