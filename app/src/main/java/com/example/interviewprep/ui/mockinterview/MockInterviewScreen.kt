package com.example.interviewprep.ui.mockinterview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MockInterviewScreen(
    sessionId: String? = null,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mock Interview",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (sessionId != null) {
            Text("Session ID: $sessionId")
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Text("This is a placeholder for the Mock Interview screen.")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = onNavigateBack) {
            Text("Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMockInterviewScreen() {
    MaterialTheme {
        MockInterviewScreen(onNavigateBack = {})
    }
} 