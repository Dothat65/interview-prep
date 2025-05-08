package com.example.interviewprep.utils

object Validators {
    private val EMAIL_REGEX = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
        "@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"
    )

    fun isValidEmail(email: String): Boolean {
        return email.matches(EMAIL_REGEX)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.MIN_PASSWORD_LENGTH
    }

    fun getEmailError(email: String): String? {
        return when {
            email.isEmpty() -> "Email is required"
            !isValidEmail(email) -> Constants.ERROR_INVALID_EMAIL
            else -> null
        }
    }

    fun getPasswordError(password: String): String? {
        return when {
            password.isEmpty() -> "Password is required"
            !isValidPassword(password) -> Constants.ERROR_PASSWORD_TOO_SHORT
            else -> null
        }
    }
} 