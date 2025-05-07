package com.example.interviewprep.ui.settings

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.interviewprep.ui.components.FloatingBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController? = null,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    // State for toggles
    var interviewReminders by remember { mutableStateOf(true) }
    var dailyPrompts by remember { mutableStateOf(true) }
    var expandedLanguage by remember { mutableStateOf(false) }
    val selectedLanguage by remember { mutableStateOf("English") }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarMessage by remember { mutableStateOf("") }

    val languages = listOf("English", "Spanish", "Arabic")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
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
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Notifications Section
                SectionTitle(title = "Notifications", icon = Icons.Default.Notifications)
                SettingToggle(
                    title = "Interview Reminders",
                    checked = interviewReminders,
                    onCheckedChange = { interviewReminders = it }
                )
                SettingToggle(
                    title = "Daily Practice Prompts",
                    checked = dailyPrompts,
                    onCheckedChange = { dailyPrompts = it }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Language Section
                SectionTitle(title = "Language", icon = Icons.Default.Language)
                ExposedDropdownMenuBox(
                    expanded = expandedLanguage,
                    onExpandedChange = { expandedLanguage = it }
                ) {
                    TextField(
                        value = selectedLanguage,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLanguage,
                        onDismissRequest = { expandedLanguage = false }
                    ) {
                        languages.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language) },
                                onClick = {
                                    expandedLanguage = false
                                    Toast.makeText(context, "Language set to $language", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Security Section
                SectionTitle(title = "Security", icon = Icons.Default.Lock)
                SettingButton(
                    title = "Change Password",
                    icon = Icons.Default.Key,
                    onClick = {
                        Toast.makeText(context, "Change password coming soon!", Toast.LENGTH_SHORT).show()
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // App Section
                SectionTitle(title = "App", icon = Icons.Default.Apps)
                SettingButton(
                    title = "Rate this App",
                    icon = Icons.Default.Star,
                    onClick = {
                        Toast.makeText(context, "Feature coming soon!", Toast.LENGTH_SHORT).show()
                    }
                )
                SettingButton(
                    title = "Send Feedback",
                    icon = Icons.Default.Email,
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:support@interviewprep.com".toUri()
                            putExtra(Intent.EXTRA_SUBJECT, "Interview Prep App Feedback")
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Legal Section
                SectionTitle(title = "Legal", icon = Icons.Default.Description)
                SettingButton(
                    title = "Privacy Policy",
                    icon = Icons.Default.Security,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, "https://interviewprep.com/privacy".toUri())
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Could not open link", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                SettingButton(
                    title = "Terms of Service",
                    icon = Icons.AutoMirrored.Filled.Article,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, "https://interviewprep.com/terms".toUri())
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Could not open link", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Out Button
                Button(
                    onClick = {
                        Toast.makeText(context, "Signed out!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.1f),
                        contentColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out")
                }

                // Add bottom spacing to prevent content from being hidden behind nav bar
                Spacer(
                    modifier = Modifier
                        .height(96.dp)
                        .navigationBarsPadding()
                )
            }

            if (showSnackbar) {
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { showSnackbar = false }) {
                            Text("DISMISS")
                        }
                    }
                ) {
                    Text(snackbarMessage)
                }
            }

            if (navController != null) {
                FloatingBottomNavigation(
                    navController = navController,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1AA260)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        )
    }
}

@Composable
private fun SettingToggle(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF1AA260),
                    checkedTrackColor = Color(0xFF1AA260).copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
private fun SettingButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1AA260),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    MaterialTheme {
        SettingsScreen()
    }
} 