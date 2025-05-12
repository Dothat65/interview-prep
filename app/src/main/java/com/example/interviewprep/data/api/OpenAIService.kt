package com.example.interviewprep.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAIService @Inject constructor() {
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val baseUrl = "https://api.openai.com/v1/chat/completions"
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    // Store the API key securely - for production you would use more secure methods
    private val apiKey = "YOUR KEY"

    private val systemMessage = JSONObject().apply {
        put("role", "system")
        put("content", "You are an expert interview coach guiding the user through interview practice. Your role is to generate clear, concise, and engaging interview questions and provide structured feedback. Only generate the question itself without any formatting, headers, or additional content.")
    }

    suspend fun generateInterviewQuestion(topic: String): String = withContext(Dispatchers.IO) {
        val messages = JSONArray().apply {
            put(systemMessage)
            put(JSONObject().apply {
                put("role", "user")
                put("content", "Generate a unique, thought-provoking $topic interview question. Ensure it is open-ended, relevant to real-world scenarios, and suitable for both voice and text responses. Avoid repetition of previous questions. Provide ONLY the question text without any labels, asterisks, or additional context.")
            })
        }

        val requestBody = JSONObject().apply {
            put("model", "gpt-4o-mini")
            put("messages", messages)
            put("max_tokens", 300)
        }

        val request = Request.Builder()
            .url(baseUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toString().toRequestBody(mediaType))
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception("OpenAI API call failed: ${response.code} - ${response.body?.string()}")
        }

        val responseBody = response.body?.string() ?: throw Exception("Empty response from OpenAI")
        val jsonResponse = JSONObject(responseBody)

        val content = jsonResponse
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")

        // Clean up any remaining markdown or formatting
        return@withContext cleanQuestionText(content)
    }

    private fun cleanQuestionText(text: String): String {
        // Remove any markdown headers or formatting
        var cleaned = text.replace(Regex("\\*\\*.*?\\*\\*"), "")
            .replace(Regex("^#+\\s+"), "")
            .replace("**Interview Question:**", "")
            .replace("**Feedback Structure:**", "")
            .replace("**", "")
            .replace("---", "")

        // Remove numbered lists and other formatting
        cleaned = cleaned.replace(Regex("\\d+\\.\\s+"), "")

        // Remove any sections about feedback structure
        val feedbackIndex = cleaned.indexOf("Feedback", ignoreCase = true)
        if (feedbackIndex > 0) {
            cleaned = cleaned.substring(0, feedbackIndex)
        }

        // Trim and remove extra whitespace and newlines
        cleaned = cleaned.trim().replace(Regex("\\n{2,}"), "\n")

        return cleaned
    }

    suspend fun generateFeedback(question: String, answer: String): String = withContext(Dispatchers.IO) {
        val messages = JSONArray().apply {
            put(systemMessage)
            put(JSONObject().apply {
                put("role", "user")
                put("content", question)
            })
            put(JSONObject().apply {
                put("role", "assistant")
                put("content", "I'll answer this interview question.")
            })
            put(JSONObject().apply {
                put("role", "user")
                put("content", "Provide a thoughtful, paragraph-style evaluation of the user's interview answer to the latest question. Discuss the strengths of the response, such as technical accuracy, clarity, and how well it was structured. Then, address any areas that could be improved, like the depth of explanation, effectiveness of communication, or the problem-solving approach. Ensure the feedback is constructive, balanced, and offers specific, actionable suggestions. Do not use any markdown formatting, headers, or numbered lists.")
            })
            put(JSONObject().apply {
                put("role", "user")
                put("content", answer)
            })
        }

        val requestBody = JSONObject().apply {
            put("model", "gpt-4o-mini")
            put("messages", messages)
            put("max_tokens", 500)
        }

        val request = Request.Builder()
            .url(baseUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toString().toRequestBody(mediaType))
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception("OpenAI API call failed: ${response.code} - ${response.body?.string()}")
        }

        val responseBody = response.body?.string() ?: throw Exception("Empty response from OpenAI")
        val jsonResponse = JSONObject(responseBody)

        val content = jsonResponse
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")

        // Clean up any remaining markdown or formatting
        return@withContext content.replace("**", "")
            .replace("#", "")
            .replace("---", "")
    }
}