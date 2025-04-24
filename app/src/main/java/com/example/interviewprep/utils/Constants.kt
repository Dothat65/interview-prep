package com.example.interviewprep.utils

object Constants {
    // Navigation Routes
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val MOCK_INTERVIEW = "mock_interview"
    const val PRACTICE = "practice"
    const val INTERVIEW_SESSION = "interview_session/{sessionId}"
    
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_INTERVIEW_SESSIONS = "interview_sessions"
    const val COLLECTION_CHAT_MESSAGES = "chat_messages"
    
    // Firebase Storage
    const val STORAGE_RESUMES = "resumes"
    
    // Validation
    const val MIN_PASSWORD_LENGTH = 6
    
    // UI
    const val ANIMATION_DURATION = 300
    
    // Error Messages
    const val ERROR_INVALID_EMAIL = "Invalid email format"
    const val ERROR_PASSWORD_TOO_SHORT = "Password must be at least 6 characters"
    const val ERROR_LOGIN_FAILED = "Login failed. Please check your credentials."
    const val ERROR_SIGNUP_FAILED = "Signup failed. Please try again."
    const val ERROR_NETWORK = "Network error. Please check your connection."
} 