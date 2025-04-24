package com.example.interviewprep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interviewprep.ui.home.HomeScreen
import com.example.interviewprep.ui.mockinterview.MockInterviewScreen
import com.example.interviewprep.ui.onboarding.LoginScreen
import com.example.interviewprep.ui.onboarding.OnboardingFlow
import com.example.interviewprep.ui.onboarding.SignUpScreen
import com.example.interviewprep.ui.practice.PracticeScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.HOME) },
                onSignUpClick = { navController.navigate(Routes.ONBOARDING) }
            )
        }
        
        composable(Routes.SIGNUP) {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate(Routes.HOME) },
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }
        
        composable(Routes.ONBOARDING) {
            OnboardingFlow(
                navController = navController,
                onComplete = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Routes.HOME) {
            HomeScreen(
                onStartInterview = { navController.navigate(Routes.MOCK_INTERVIEW) },
                onPracticeClick = { navController.navigate(Routes.PRACTICE) },
                onInterviewClick = { sessionId ->
                    navController.navigate(Routes.interviewSession(sessionId))
                }
            )
        }
        
        composable(Routes.MOCK_INTERVIEW) {
            MockInterviewScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.PRACTICE) {
            PracticeScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Routes.INTERVIEW_SESSION,
            arguments = listOf(
                navArgument("sessionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: return@composable
            MockInterviewScreen(
                sessionId = sessionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 