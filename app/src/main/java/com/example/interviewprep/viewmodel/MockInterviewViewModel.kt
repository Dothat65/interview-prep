package com.example.interviewprep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewprep.data.api.OpenAIService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MockInterviewViewModel @Inject constructor(
    private val openAIService: OpenAIService,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<MockInterviewState>(MockInterviewState.Selection)
    val uiState: StateFlow<MockInterviewState> = _uiState

    private val _currentQuestion = MutableStateFlow<String>("")
    val currentQuestion: StateFlow<String> = _currentQuestion

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer

    private val _feedback = MutableStateFlow<String?>(null)
    val feedback: StateFlow<String?> = _feedback

    private val _questionNumber = MutableStateFlow(1)
    val questionNumber: StateFlow<Int> = _questionNumber

    private val _totalQuestions = MutableStateFlow(5)
    val totalQuestions: StateFlow<Int> = _totalQuestions

    private val _history = MutableStateFlow<List<InterviewEntry>>(emptyList())
    val history: StateFlow<List<InterviewEntry>> = _history

    private val _currentTopic = MutableStateFlow("Software Engineering")
    val currentTopic: StateFlow<String> = _currentTopic

    fun updateAnswer(text: String) {
        _answer.value = text
    }

    fun startInterview(topic: String, questionCount: Int) {
        _currentTopic.value = topic
        _totalQuestions.value = questionCount

        viewModelScope.launch {
            _uiState.value = MockInterviewState.Loading

            try {
                // Reset state
                _questionNumber.value = 1
                _answer.value = ""
                _feedback.value = null
                _history.value = emptyList()

                // Get first question
                try {
                    val question = openAIService.generateInterviewQuestion(topic)
                    _currentQuestion.value = question
                } catch (e: Exception) {
                    _uiState.value = MockInterviewState.Error("Failed to generate question: ${e.message}")
                    return@launch
                }

                _uiState.value = MockInterviewState.InProgress
            } catch (e: Exception) {
                _uiState.value = MockInterviewState.Error("Failed to start interview: ${e.message}")
            }
        }
    }

    fun submitAnswer() {
        if (_answer.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = MockInterviewState.Loading

            try {
                // Get feedback
                val feedbackText = openAIService.generateFeedback(
                    question = _currentQuestion.value,
                    answer = _answer.value
                )
                _feedback.value = feedbackText

                // Add to history
                _history.value = _history.value + InterviewEntry(
                    question = _currentQuestion.value,
                    answer = _answer.value,
                    feedback = feedbackText
                )

                // Make sure we transition to the feedback state
                _uiState.value = MockInterviewState.FeedbackReceived
            } catch (e: Exception) {
                _uiState.value = MockInterviewState.Error("Failed to get feedback: ${e.message}")
            }
        }
    }

    fun moveToNextQuestion() {
        viewModelScope.launch {
            if (_questionNumber.value >= _totalQuestions.value) {
                _uiState.value = MockInterviewState.Completed
                return@launch
            }

            _questionNumber.value += 1
            _answer.value = ""
            _feedback.value = null
            _uiState.value = MockInterviewState.Loading

            try {
                // Get next question
                val question = openAIService.generateInterviewQuestion(_currentTopic.value)
                _currentQuestion.value = question
                _uiState.value = MockInterviewState.InProgress
            } catch (e: Exception) {
                _uiState.value = MockInterviewState.Error("Failed to get next question: ${e.message}")
            }
        }
    }

    fun goToTopicSelection() {
        _uiState.value = MockInterviewState.Selection
    }

    data class InterviewEntry(
        val question: String,
        val answer: String,
        val feedback: String
    )
}

sealed class MockInterviewState {
    object Selection : MockInterviewState()
    object Initial : MockInterviewState()
    object Loading : MockInterviewState()
    object InProgress : MockInterviewState()
    object FeedbackReceived : MockInterviewState()
    object Completed : MockInterviewState()
    data class Error(val message: String) : MockInterviewState()
}