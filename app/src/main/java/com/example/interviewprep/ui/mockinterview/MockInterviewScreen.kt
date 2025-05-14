package com.example.interviewprep.ui.mockinterview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Import for the back arrow icon
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewprep.ui.components.LoadingIndicator
import com.example.interviewprep.viewmodel.MockInterviewState
import com.example.interviewprep.viewmodel.MockInterviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockInterviewScreen(
    sessionId: String? = null,
    onNavigateBack: () -> Unit, // This lambda will be called when the back button is clicked
    viewModel: MockInterviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentQuestion by viewModel.currentQuestion.collectAsStateWithLifecycle()
    val answer by viewModel.answer.collectAsStateWithLifecycle()
    val feedback by viewModel.feedback.collectAsStateWithLifecycle()
    val questionNumber by viewModel.questionNumber.collectAsStateWithLifecycle()
    val totalQuestions by viewModel.totalQuestions.collectAsStateWithLifecycle()
    val history by viewModel.history.collectAsStateWithLifecycle()
    val currentTopic by viewModel.currentTopic.collectAsStateWithLifecycle()

    // Topic selection state
    var selectedTopic by remember { mutableStateOf("Software Engineering") }
    var questionCount by remember { mutableStateOf(5) }
    var expandedTopicDropdown by remember { mutableStateOf(false) }

    // Expanded list of topics from the web app
    val topics = listOf(
        "Software Engineering",
        "Data Science",
        "Product Management",
        "UX Design",
        "Leadership",
        "Project Management",
        "System Design",
        "Web Development",
        "Mobile Development",
        "DevOps",
        "Cloud Computing",
        "Cybersecurity",
        "Machine Learning",
        "Artificial Intelligence",
        "Blockchain",
        "Big Data",
        "Database Management",
        "Network Engineering",
        "Technical Writing",
        "Quality Assurance",
        "Marketing",
        "Sales",
        "Customer Service",
        "Human Resources",
        "Finance",
        "Business Development",
        "Consulting",
        "Communication Skills",
        "Problem Solving",
        "Critical Thinking",
        "General"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mock Interview") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E5EAE),
                    titleContentColor = Color.White
                ),
                // Add the navigationIcon slot for the back button
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Call the onNavigateBack lambda
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home" // Accessibility description
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is MockInterviewState.Selection -> {
                    // Topic selection UI integrated directly
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mock Interview Setup",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 24.dp)
                        )

                        // Topic Dropdown
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Select Topic",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                ExposedDropdownMenuBox(
                                    expanded = expandedTopicDropdown,
                                    onExpandedChange = { expandedTopicDropdown = it }
                                ) {
                                    OutlinedTextField(
                                        value = selectedTopic,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            Icon(
                                                Icons.Filled.ArrowDropDown,
                                                contentDescription = "Dropdown arrow"
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expandedTopicDropdown,
                                        onDismissRequest = { expandedTopicDropdown = false },
                                        modifier = Modifier.heightIn(max = 350.dp) // Limit height for scrolling
                                    ) {
                                        topics.forEach { topic ->
                                            DropdownMenuItem(
                                                text = { Text(topic) },
                                                onClick = {
                                                    selectedTopic = topic
                                                    expandedTopicDropdown = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Number of questions selection
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Number of Questions",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = "$questionCount Questions",
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Slider(
                                    value = questionCount.toFloat(),
                                    onValueChange = { questionCount = it.toInt() },
                                    valueRange = 1f..15f,
                                    steps = 13,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    listOf(3, 5, 7, 10).forEach { count ->
                                        Button(
                                            onClick = { questionCount = count },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (questionCount == count)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.surfaceVariant
                                            )
                                        ) {
                                            Text(count.toString())
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.startInterview(selectedTopic, questionCount) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4E5EAE)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Start Interview")
                        }
                    }
                }

                is MockInterviewState.Loading -> {
                    LoadingIndicator()
                }

                is MockInterviewState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = (uiState as MockInterviewState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Button(onClick = { viewModel.goToTopicSelection() }) {
                            Text("Go Back")
                        }
                    }
                }

                is MockInterviewState.Completed -> {
                    InterviewSummary(
                        history = history,
                        onFinish = { viewModel.goToTopicSelection() }
                    )
                }

                else -> { // Covers MockInterviewState.InterviewInProgress and MockInterviewState.FeedbackReceived
                    InterviewInProgress(
                        questionNumber = questionNumber,
                        totalQuestions = totalQuestions,
                        question = currentQuestion,
                        answer = answer,
                        feedback = feedback,
                        topic = currentTopic,
                        onAnswerChange = viewModel::updateAnswer,
                        onSubmit = viewModel::submitAnswer,
                        onNext = viewModel::moveToNextQuestion,
                        showFeedback = uiState is MockInterviewState.FeedbackReceived
                    )
                }
            }
        }
    }
}

@Composable
private fun InterviewInProgress(
    questionNumber: Int,
    totalQuestions: Int,
    question: String,
    answer: String,
    feedback: String?,
    topic: String,
    onAnswerChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit,
    showFeedback: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Progress indicator
        LinearProgressIndicator(
            progress = { questionNumber.toFloat() / totalQuestions },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = Color(0xFF4E5EAE),
            trackColor = Color(0xFFE0E0E0)
        )

        Text(
            text = "Question $questionNumber of $totalQuestions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Topic indicator
        Text(
            text = "Topic: $topic",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Question display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F7FF)
            )
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Answer input
        OutlinedTextField(
            value = answer,
            onValueChange = onAnswerChange,
            label = { Text("Your Answer") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp),
            enabled = !showFeedback,
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button or feedback section
        if (!showFeedback) {
            Button(
                onClick = onSubmit,
                enabled = answer.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4E5EAE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Answer")
            }
        }
        else { // showFeedback is true
            // Feedback display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F7FF)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Feedback",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = feedback ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4E5EAE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (questionNumber >= totalQuestions) "Finish Interview" else "Next Question")
            }
        }
    }
}

@Composable
private fun InterviewSummary(
    history: List<MockInterviewViewModel.InterviewEntry>,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Interview Summary",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(history) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F7FF)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Question:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = entry.question,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(
                            text = "Your Answer:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = entry.answer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text(
                            text = "Feedback:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = entry.feedback,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4E5EAE)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Return to Home")
        }
    }
}
