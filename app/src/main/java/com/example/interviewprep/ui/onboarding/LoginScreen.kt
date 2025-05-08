package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewprep.R
import com.example.interviewprep.ui.components.LoadingIndicator
import com.example.interviewprep.ui.components.PrimaryButton
import com.example.interviewprep.utils.Validators
import com.example.interviewprep.viewmodel.AuthResult
import com.example.interviewprep.viewmodel.AuthViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.authState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA9FF80))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.interviewlogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        Text("Login", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { 
                email = it
                emailError = Validators.getEmailError(it)
            },
            label = { Text("Email") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it, color = Color.Red) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { 
                password = it
                passwordError = Validators.getPasswordError(it)
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it, color = Color.Red) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            text = "Login",
            enabled = emailError == null && passwordError == null && email.isNotEmpty() && password.isNotEmpty(),
            onClick = {
                viewModel.login(email, password)
            }
        )

        TextButton(onClick = onSignUpClick) {
            Text("Don't have an account? Sign up")
        }

        when (state) {
            is AuthResult.Loading -> LoadingIndicator()
            is AuthResult.Success -> LaunchedEffect(Unit) {
                viewModel.resetState()
                onLoginSuccess()
            }
            is AuthResult.Error -> Text(
                text = (state as AuthResult.Error).message ?: "Login failed",
                color = Color.Red
            )
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        LoginScreen(
            onLoginSuccess = {},
            onSignUpClick = {}
        )
    }
} 