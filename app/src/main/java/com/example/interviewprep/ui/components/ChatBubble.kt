package com.example.interviewprep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.interviewprep.data.model.MessageSender

@Composable
fun ChatBubble(
    message: String,
    sender: MessageSender,
    feedback: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (sender == MessageSender.USER) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (sender == MessageSender.USER) 16.dp else 4.dp,
                        bottomEnd = if (sender == MessageSender.USER) 4.dp else 16.dp
                    )
                )
                .background(
                    if (sender == MessageSender.USER)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary
                )
                .padding(12.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = if (sender == MessageSender.USER)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSecondary
            )
        }

        if (feedback != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedback,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = if (sender == MessageSender.USER) TextAlign.End else TextAlign.Start,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
} 