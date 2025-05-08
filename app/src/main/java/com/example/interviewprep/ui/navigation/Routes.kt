package com.example.interviewprep.ui.navigation

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val MOCK_INTERVIEW = "mock_interview"
    const val PRACTICE = "practice"
    const val INTERVIEW_SESSION = "interview_session/{sessionId}"
    const val ONBOARDING = "onboarding"
    const val ACCOUNT = "account"
    const val SETTINGS = "settings"
    
    fun interviewSession(sessionId: String) = "interview_session/$sessionId"
} 