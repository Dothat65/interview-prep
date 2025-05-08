package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewprep.R
import com.example.interviewprep.ui.components.GreenButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadResumeScreen(
    hasUploadedResume: Boolean,
    onUploadResume: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1AA260))
    ) {
        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(8.dp),
                tint = Color.White
            )
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo and title
            Image(
                painter = painterResource(id = R.drawable.interviewlogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
            )
            
            Text(
                text = "Interview Prep Lite",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            if (hasUploadedResume) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Resume Uploaded",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "Resume Uploaded",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                IconButton(
                    onClick = onUploadResume,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Upload,
                        contentDescription = "Upload Resume",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Upload Resume",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            GreenButton(
                text = if (hasUploadedResume) "Next" else "Skip",
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUploadResumeScreen() {
    var hasUploadedResume by remember { mutableStateOf(false) }
    UploadResumeScreen(
        hasUploadedResume = hasUploadedResume,
        onUploadResume = { hasUploadedResume = true },
        onNext = {},
        onBack = {}
    )
}
