package com.example.interviewprep.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.interviewprep.R
import com.example.interviewprep.ui.components.FloatingBottomNavigation
import com.example.interviewprep.data.model.InterviewStatus
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    onStartInterview: () -> Unit = {},
    onPracticeClick: () -> Unit = {},
    onInterviewClick: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFA9FF80),
                            Color.White
                        )
                    )
                )
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.interviewlogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(72.dp)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Greeting Box
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFA9FF80)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hello, John!",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        )
                        Text(
                            text = "Welcome back!",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Mock Points: 85",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.8f)
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        // Placeholder for profile picture
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF00C853))
                        .clickable(onClick = onStartInterview),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Start Mock\nInterview",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF00C853))
                        .clickable(onClick = onPracticeClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Practice\nQuestions",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Resume Interviews Section
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Resume Interviews",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
                Text(
                    text = "Pick up where you left off:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Sample interview cards
                val interviews = listOf(
                    InterviewData(
                        "Data Analyst Interview",
                        "What is your experience with SQL?",
                        75f,
                        InterviewStatus.IN_PROGRESS,
                        3,
                        10,
                        "15 min left",
                        85,
                        "Software Engineering",
                        "2 days ago"
                    ),
                    InterviewData(
                        "Software Engineer Interview",
                        "Explain the concept of dependency injection",
                        60f,
                        InterviewStatus.IN_PROGRESS,
                        5,
                        15,
                        "25 min left",
                        70,
                        "Software Development",
                        "1 day ago"
                    ),
                    InterviewData(
                        "Product Manager Interview",
                        "How do you prioritize features?",
                        90f,
                        InterviewStatus.COMPLETED,
                        10,
                        10,
                        "Completed",
                        95,
                        "Product Management",
                        "3 days ago"
                    )
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(start = 16.dp, end = 8.dp)
                ) {
                    items(interviews) { interview ->
                        Surface(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .clickable { onInterviewClick(interview.title) },
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = interview.title,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "Last question: ${interview.lastQuestion}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLines = 1,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                                
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = interview.timeLeft,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "Points: ${interview.points}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    }
                                    
                                    Text(
                                        text = "Job Field: ${interview.jobField}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    
                                    Text(
                                        text = "Last viewed: ${interview.lastViewed}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    InterviewProgressBar(interview.progress)
                                }
                            }
                        }
                    }
                }
            }

            // Add bottom spacing to prevent content from being hidden behind nav bar
            Spacer(
                modifier = Modifier
                    .height(96.dp)
                    .navigationBarsPadding()
            )
        }

        FloatingBottomNavigation(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

private data class InterviewData(
    val title: String,
    val lastQuestion: String,
    val progress: Float,
    val status: InterviewStatus,
    val questionsCompleted: Int,
    val totalQuestions: Int,
    val timeLeft: String,
    val points: Int,
    val jobField: String,
    val lastViewed: String
)

@Composable
private fun InterviewProgressBar(progress: Float) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = Color(0xFF1AA260),
        trackColor = Color.LightGray
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(navController = rememberNavController())
    }
} 