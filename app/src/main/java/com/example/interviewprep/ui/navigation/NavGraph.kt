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
// REMOVE the import for PracticeScreen if it's still here
// import com.example.interviewprep.ui.practice.PracticeScreen

// IMPORT the new PopularQuestionsScreen
import com.example.interviewprep.ui.practice.PopularQuestionsScreen // <-- Add this import

import com.example.interviewprep.ui.account.AccountScreen
import com.example.interviewprep.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN // Or your desired start destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.HOME) },
                onSignUpClick = { navController.navigate(Routes.ONBOARDING) } // Assuming ONBOARDING handles initial signup flow
            )
        }

        composable(Routes.SIGNUP) {
            // If you have a separate signup screen outside onboarding, keep this.
            // If signup is part of the onboarding flow, you might remove this.
            SignUpScreen(
                onSignUpSuccess = { navController.navigate(Routes.HOME) },
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingFlow(
                navController = navController,
                onComplete = {
                    // Navigate to HOME and remove login/onboarding from the back stack
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController, // Pass navController to HomeScreen if needed for internal navigation
                onStartInterview = { navController.navigate(Routes.MOCK_INTERVIEW) },
                // Update this navigation call to the new PopularQuestionsScreen route
                onPracticeClick = { navController.navigate(Routes.PRACTICE) }, // Keep the route, but the composable will change below
                onInterviewClick = { sessionId ->
                    navController.navigate(Routes.interviewSession(sessionId))
                }
            )
        }

        composable(Routes.ACCOUNT) {
            AccountScreen(
                navController = navController, // Pass navController if needed
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                navController = navController, // Pass navController if needed
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.MOCK_INTERVIEW) {
            // Main Mock Interview Setup screen
            MockInterviewScreen(
                onNavigateBack = { navController.popBackStack() } // Navigate back from setup
            )
        }

        // UPDATE this composable block to use PopularQuestionsScreen
        composable(Routes.PRACTICE) {
            PopularQuestionsScreen( // <-- Use the new composable here
                onNavigateBack = { navController.popBackStack() } // Pass the back navigation lambda
            )
        }

        composable(
            route = Routes.INTERVIEW_SESSION,
            arguments = listOf(
                navArgument("sessionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: return@composable
            // Specific Interview Session screen
            MockInterviewScreen( // Assuming MockInterviewScreen handles displaying a specific session
                sessionId = sessionId,
                onNavigateBack = { navController.popBackStack() } // Navigate back from session
            )
        }
    }
}
