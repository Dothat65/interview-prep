package com.example.interviewprep.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewprep.R
import androidx.navigation.NavController
import com.example.interviewprep.ui.components.FloatingBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController? = null,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // State for dropdowns
    var expandedAgeGroup by remember { mutableStateOf(false) }
    var expandedJobField by remember { mutableStateOf(false) }
    var selectedAgeGroup by remember { mutableStateOf("25-34") }
    var selectedJobField by remember { mutableStateOf("Software Engineering") }
    var initialAgeGroup by remember { mutableStateOf(selectedAgeGroup) }
    var initialJobField by remember { mutableStateOf(selectedJobField) }
    var showSnackbar by remember { mutableStateOf(false) }

    val ageGroups = listOf("18-24", "25-34", "35-44", "45+")
    val jobFields = listOf(
        "Software Engineering",
        "Data Science",
        "Product Management",
        "UX Design",
        "Marketing",
        "Sales",
        "Other"
    )

    val hasChanges = selectedAgeGroup != initialAgeGroup || selectedJobField != initialJobField

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1AA260))
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFA9FF80)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.interviewlogo),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                        IconButton(
                            onClick = { /* TODO: Image picker */ },
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.BottomEnd)
                                .background(Color(0xFF1AA260), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile Photo",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Hello, Bilal Ahmad",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Text(
                        text = "bilal.ahmeed@gmail.com",
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
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Edit Information Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Edit Information",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Age Group Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedAgeGroup,
                        onExpandedChange = { expandedAgeGroup = it }
                    ) {
                        OutlinedTextField(
                            value = selectedAgeGroup,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Age Group") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAgeGroup) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color(0xFF1AA260),
                                focusedLabelColor = Color(0xFF1AA260)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedAgeGroup,
                            onDismissRequest = { expandedAgeGroup = false }
                        ) {
                            ageGroups.forEach { age ->
                                DropdownMenuItem(
                                    text = { Text(age) },
                                    onClick = {
                                        selectedAgeGroup = age
                                        expandedAgeGroup = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Job Field Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedJobField,
                        onExpandedChange = { expandedJobField = it }
                    ) {
                        OutlinedTextField(
                            value = selectedJobField,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Job Field") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedJobField) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color(0xFF1AA260),
                                focusedLabelColor = Color(0xFF1AA260)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedJobField,
                            onDismissRequest = { expandedJobField = false }
                        ) {
                            jobFields.forEach { field ->
                                DropdownMenuItem(
                                    text = { Text(field) },
                                    onClick = {
                                        selectedJobField = field
                                        expandedJobField = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Save Button - Only show if changes were made
                    if (hasChanges) {
                        Button(
                            onClick = {
                                // Update initial values
                                initialAgeGroup = selectedAgeGroup
                                initialJobField = selectedJobField
                                showSnackbar = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1AA260)
                            )
                        ) {
                            Text("Save Changes")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Links Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    QuickLinkItem(
                        icon = Icons.Default.Description,
                        text = "My Interviews",
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    QuickLinkItem(
                        icon = Icons.Default.Upload,
                        text = "Resume Uploads",
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    QuickLinkItem(
                        icon = Icons.Default.BarChart,
                        text = "Performance",
                        onClick = { /* TODO */ }
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(96.dp)
                    .navigationBarsPadding()
            )
        }

        // Show snackbar if changes were saved
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
                Text("Changes saved successfully!")
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

@Composable
private fun QuickLinkItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
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
                    text = text,
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
fun PreviewAccountScreen() {
    MaterialTheme {
        AccountScreen()
    }
} 