package com.example.interviewprep.ui.onboarding

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.interviewprep.ui.components.LoadingScreen
import com.example.interviewprep.ui.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun OnboardingFlow(
    onboardingViewModel: OnboardingViewModel = viewModel(),
    navController: NavController,
    onComplete: () -> Unit = {}
) {
    val step = onboardingViewModel.currentStep

    Crossfade(targetState = step, modifier = Modifier.fillMaxSize()) { currentStep ->
        when (currentStep) {
            0 -> LoadingScreenWithAutoAdvance { onboardingViewModel.nextStep() }

            1 -> EnterNameScreen(
                firstName = onboardingViewModel.firstName,
                lastName = onboardingViewModel.lastName,
                onFirstNameChange = { onboardingViewModel.firstName = it },
                onLastNameChange = { onboardingViewModel.lastName = it },
                onNext = { onboardingViewModel.nextStep() },
                onBack = { 
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )

            2 -> SelectGenderScreen(
                gender = onboardingViewModel.gender,
                onGenderChange = { onboardingViewModel.gender = it },
                onNext = { onboardingViewModel.nextStep() },
                onBack = { onboardingViewModel.previousStep() }
            )

            3 -> SelectAgeGroupScreen(
                ageGroup = onboardingViewModel.ageGroup,
                onAgeGroupChange = { onboardingViewModel.ageGroup = it },
                onNext = { onboardingViewModel.nextStep() },
                onBack = { onboardingViewModel.previousStep() }
            )

            4 -> SelectJobFieldScreen(
                jobField = onboardingViewModel.jobField,
                onJobFieldChange = { onboardingViewModel.jobField = it },
                onNext = { onboardingViewModel.nextStep() },
                onBack = { onboardingViewModel.previousStep() }
            )

            5 -> UploadResumeScreen(
                hasUploadedResume = onboardingViewModel.resumeUploaded,
                onUploadResume = { onboardingViewModel.resumeUploaded = true },
                onNext = {
                    Log.d("Onboarding", "Onboarding Complete!")
                    onComplete()
                },
                onBack = { onboardingViewModel.previousStep() }
            )
        }
    }
}

@Composable
private fun LoadingScreenWithAutoAdvance(onTimeout: () -> Unit) {
    LaunchedEffect(true) {
        delay(2000)
        onTimeout()
    }
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardingFlow() {
    val dummyNavController = rememberNavController()
    val vm = OnboardingViewModel()
    OnboardingFlow(
        onboardingViewModel = vm,
        navController = dummyNavController
    )
}
