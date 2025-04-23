package com.example.interviewprep.ui.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var gender by mutableStateOf("")
    var ageGroup by mutableStateOf("")
    var jobField by mutableStateOf("")
    var resumeUploaded by mutableStateOf(false)
    var currentStep by mutableStateOf(0)

    fun reset() {
        firstName = ""
        lastName = ""
        gender = ""
        ageGroup = ""
        jobField = ""
        resumeUploaded = false
        currentStep = 0
    }

    fun nextStep() {
        currentStep++
    }

    fun previousStep() {
        if (currentStep > 1) currentStep--
    }
} 