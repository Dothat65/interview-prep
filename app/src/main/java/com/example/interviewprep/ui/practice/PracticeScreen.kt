package com.example.interviewprep.ui.practice

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Import for back arrow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularQuestionsScreen(
    onNavigateBack: () -> Unit // Lambda for back navigation
) {
    // More extensive sample data for popular industries and questions
    val popularQuestionsData = remember {
        listOf(
            IndustryQuestions(
                name = "Software Engineering",
                description = "Common questions covering data structures, algorithms, system design, and behavioral topics.",
                questions = listOf(
                    "Tell me about a time you faced a challenging bug and how you debugged it.",
                    "Explain the concept of RESTful APIs and their key principles.",
                    "Describe the difference between processes and threads and when to use each.",
                    "How would you design a URL shortening service, considering scalability?",
                    "What is your favorite programming language or technology and why?",
                    "Explain polymorphism and inheritance in object-oriented programming.",
                    "How do you approach writing unit tests?",
                    "Describe a time you had to work with a difficult team member.",
                    "What are the trade-offs of using a microservices architecture?",
                    "Explain the concept of eventual consistency."
                )
            ),
            IndustryQuestions(
                name = "Data Science",
                description = "Questions focusing on statistics, machine learning algorithms, data manipulation, and model evaluation.",
                questions = listOf(
                    "Explain the bias-variance tradeoff in machine learning.",
                    "What is a confusion matrix and how is it used to evaluate a classification model?",
                    "Describe the typical steps involved in a data science project lifecycle.",
                    "How do you handle missing data in a dataset?",
                    "What are the assumptions of linear regression, and how do you check them?",
                    "Explain the difference between L1 and L2 regularization.",
                    "How does a Random Forest algorithm work?",
                    "What is cross-validation and why is it important?",
                    "Describe a time you had to explain a complex model to a non-technical audience.",
                    "What are some common challenges in deploying machine learning models?"
                )
            ),
            IndustryQuestions(
                name = "Product Management",
                description = "Questions about product strategy, development lifecycle, market analysis, and stakeholder management.",
                questions = listOf(
                    "How do you prioritize features when you have limited resources?",
                    "Describe a product you love and explain what makes it successful.",
                    "How do you define and measure product success?",
                    "Tell me about a time you launched a product that failed and what you learned.",
                    "How do you handle conflict with engineering, design, or marketing teams?",
                    "Describe your process for gathering and analyzing user feedback.",
                    "How do you stay informed about market trends and competitor products?",
                    "What is the difference between a product roadmap and a backlog?",
                    "Explain a time you had to say 'no' to a feature request.",
                    "How do you communicate product vision and strategy to different audiences?"
                )
            ),
            IndustryQuestions(
                name = "UX Design",
                description = "Questions covering design process, user research methods, usability principles, and collaboration.",
                questions = listOf(
                    "Describe your typical UX design process from start to finish.",
                    "How do you conduct user research, and what methods do you prefer?",
                    "Tell me about a challenging design problem you've faced and how you solved it.",
                    "What is the difference between UI and UX design?",
                    "How do you handle constructive criticism or feedback on your designs?",
                    "Explain the importance of accessibility in design.",
                    "How do you collaborate with product managers and engineers?",
                    "Describe a time you had to advocate for the user.",
                    "What design tools are you most proficient with?",
                    "How do you measure the success of your designs?"
                )
            ),
            IndustryQuestions(
                name = "DevOps",
                description = "Questions related to continuous integration/continuous deployment (CI/CD), infrastructure, automation, and monitoring.",
                questions = listOf(
                    "Explain the concept of Infrastructure as Code (IaC).",
                    "Describe your experience with CI/CD pipelines.",
                    "What are some common tools used in DevOps?",
                    "How do you monitor system performance and health?",
                    "Explain the difference between Docker and Kubernetes.",
                    "How do you handle security in a DevOps environment?",
                    "Describe a time you had to troubleshoot a production issue.",
                    "What is the importance of automation in DevOps?",
                    "How do you ensure system reliability and uptime?",
                    "Explain the concept of immutable infrastructure."
                )
            ),
            IndustryQuestions(
                name = "Behavorial",
                description = "Standard behavioral and situational questions applicable to most interviews.",
                questions = listOf(
                    "Tell me about yourself.",
                    "What are your greatest strengths and weaknesses?",
                    "Why are you interested in this position and our company?",
                    "Where do you see yourself in 5 years?",
                    "How do you handle pressure or stressful situations?",
                    "Describe a time you failed and what you learned from it.",
                    "How do you stay motivated?",
                    "What are your salary expectations?",
                    "Do you have any questions for me?",
                    "Tell me about a time you had to learn something new quickly."
                )
            )
            // Add more industries and questions here to make it even more thorough
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Popular Questions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E5EAE), // Consistent color
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    // Back button in the TopAppBar
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Add some padding
        ) {
            items(popularQuestionsData) { industry ->
                IndustryQuestionCard(industry = industry)
            }
        }
    }
}

@Composable
fun IndustryQuestionCard(industry: IndustryQuestions) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
        label = "arrowRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Spacing between cards
            .clickable { expanded = !expanded }, // Make the card clickable to expand/collapse
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Add subtle shadow
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Use a variant color
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize() // Animate expansion/collapse
                .padding(16.dp) // Inner padding
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = industry.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f) // Allow text to take available space
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.rotate(rotationAngle) // Rotate icon
                )
            }

            if (expanded) {
                // Display industry description if available
                if (industry.description.isNotBlank()) {
                    Text(
                        text = industry.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp)) // Space before questions if no description
                }


                Column {
                    industry.questions.forEachIndexed { index, question ->
                        Text(
                            text = "${index + 1}. $question",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 4.dp) // Spacing between questions
                        )
                    }
                }
            }
        }
    }
}

// Data class to hold industry name, description, and its questions
data class IndustryQuestions(
    val name: String,
    val description: String = "", // Added description field
    val questions: List<String>
)

@Preview(showBackground = true)
@Composable
fun PreviewPopularQuestionsScreen() {
    MaterialTheme {
        PopularQuestionsScreen(onNavigateBack = {})
    }
}
