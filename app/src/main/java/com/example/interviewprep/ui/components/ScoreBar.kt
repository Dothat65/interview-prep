package com.example.interviewprep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ScoreBar(
    score: Int,
    maxScore: Int = 100,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Background bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        // Score bar
        Box(
            modifier = Modifier
                .fillMaxWidth(score.toFloat() / maxScore)
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(getScoreColor(score))
        )

        // Score text
        Text(
            text = "$score/$maxScore",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun getScoreColor(score: Int): Color {
    return when {
        score >= 80 -> Color(0xFF4CAF50) // Green
        score >= 60 -> Color(0xFFFFC107) // Yellow
        else -> Color(0xFFF44336) // Red
    }
} 