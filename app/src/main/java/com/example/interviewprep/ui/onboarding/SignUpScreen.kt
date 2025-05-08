package com.example.interviewprep.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interviewprep.ui.components.LoadingIndicator
import com.example.interviewprep.ui.components.PrimaryButton
import com.example.interviewprep.utils.Validators
import com.example.interviewprep.viewmodel.AuthResult
import com.example.interviewprep.viewmodel.AuthViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.authState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sign Up", style = MaterialTheme.typography.titleLarge)

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
            text = "Sign Up",
            enabled = emailError == null && passwordError == null && email.isNotEmpty() && password.isNotEmpty(),
            onClick = {
                viewModel.signUp(email, password)
            }
        )

        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Login")
        }

        when (state) {
            is AuthResult.Loading -> LoadingIndicator()
            is AuthResult.Success -> LaunchedEffect(Unit) {
                viewModel.resetState()
                onSignUpSuccess()
            }
            is AuthResult.Error -> Text(
                text = (state as AuthResult.Error).message ?: "Signup failed",
                color = Color.Red
            )
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    MaterialTheme {
        SignUpScreen(
            onSignUpSuccess = {},
            onLoginClick = {}
        )
    }
} 