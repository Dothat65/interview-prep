package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewprep.R
import com.example.interviewprep.ui.components.CustomTextField
import com.example.interviewprep.ui.components.GreenButton

@Composable
fun EnterNameScreen(
    firstName: String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    currentStep: Int = 1
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1AA260)) // Dark green background
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Back button top left
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

        // Name fields
        CustomTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = "First Name",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = "Last Name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        GreenButton(
            text = "Next",
            onClick = onNext,
            enabled = firstName.isNotBlank() && lastName.isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEnterNameScreen() {
    EnterNameScreen(
        firstName = "",
        lastName = "",
        onFirstNameChange = {},
        onLastNameChange = {},
        onNext = {},
        onBack = {},
        currentStep = 1
    )
}
