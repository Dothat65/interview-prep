package com.example.interviewprep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.interviewprep.ui.navigation.Routes

@Composable
fun FloatingBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Account Button (Left)
            FloatingNavButton(
                icon = Icons.Filled.Person,
                isSelected = currentRoute == Routes.ACCOUNT,
                onClick = { 
                    if (currentRoute != Routes.ACCOUNT) {
                        navController.navigate(Routes.ACCOUNT) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                size = 48.dp
            )
            
            // Spacer to account for the larger center button
            Spacer(modifier = Modifier.width(8.dp))
            
            // Home Button (Center)
            FloatingNavButton(
                icon = Icons.Filled.Home,
                isSelected = currentRoute == Routes.HOME,
                onClick = { 
                    if (currentRoute != Routes.HOME) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                },
                size = 64.dp,
                elevation = 8.dp,
                modifier = Modifier.offset(y = (-8).dp)
            )
            
            // Spacer to account for the larger center button
            Spacer(modifier = Modifier.width(8.dp))
            
            // Settings Button (Right)
            FloatingNavButton(
                icon = Icons.Filled.Settings,
                isSelected = currentRoute == Routes.SETTINGS,
                onClick = {
                    if (currentRoute != Routes.SETTINGS) {
                        navController.navigate(Routes.SETTINGS) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                size = 48.dp
            )
        }
    }
}

@Composable
private fun FloatingNavButton(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    size: Dp,
    elevation: Dp = 4.dp,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(size)
            .shadow(elevation, CircleShape)
            .clip(CircleShape)
            .background(
                if (isSelected) Color(0xFF1AA260)
                else Color.White
            )
            .zIndex(if (size >= 50.dp) 1f else 0f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) Color.White else Color(0xFF757575),
            modifier = Modifier.size(size / 2)
        )
    }
} 