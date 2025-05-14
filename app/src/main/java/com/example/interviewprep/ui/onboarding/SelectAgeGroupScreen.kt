package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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
fun SelectAgeGroupScreen(
    ageGroup: String,
    onAgeGroupChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    currentStep: Int = 3
) {
    val ageGroupOptions = listOf(
        "18-24",
        "25-34",
        "35-44",
        "45-54",
        "55+"
    )
    var expanded by remember { mutableStateOf(false) }

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

        // Age group selection
        Text(
            text = "Select your age group",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Dropdown menu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = ageGroup,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .background(Color.White, CircleShape)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ageGroupOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onAgeGroupChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        GreenButton(
            text = "Next",
            onClick = onNext,
            enabled = ageGroup.isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectAgeGroupScreen() {
    SelectAgeGroupScreen(
        ageGroup = "",
        onAgeGroupChange = {},
        onNext = {},
        onBack = {},
        currentStep = 3
    )
}
