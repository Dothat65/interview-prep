package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
    onBack: () -> Unit,
    currentStep: Int = 5
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1AA260))
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.interviewlogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(64.dp)
        )

        // App Name
        Text(
            text = "Interview Prep Lite",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Progress Bar
        LinearProgressIndicator(
            progress = currentStep / 5f,
            color = Color.White,
            trackColor = Color.White.copy(alpha = 0.3f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .padding(bottom = 24.dp)
        )

        // Upload resume section
        Text(
            text = "Upload your resume",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Upload button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onUploadResume)
                .background(
                    if (hasUploadedResume) Color.White.copy(alpha = 0.2f)
                    else Color.Transparent
                )
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = if (hasUploadedResume) "Resume Uploaded ✓" else "Click to upload resume",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        GreenButton(
            text = "Next",
            onClick = onNext,
            enabled = hasUploadedResume
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUploadResumeScreen() {
    UploadResumeScreen(
        hasUploadedResume = false,
        onUploadResume = {},
        onNext = {},
        onBack = {},
        currentStep = 5
    )
}
