package com.example.interviewprep.data.firebase

import com.example.interviewprep.data.model.ChatMessage
import com.example.interviewprep.data.model.InterviewSession
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import android.net.Uri

@Singleton
class FirebaseService @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    // Auth operations
    suspend fun signUp(email: String, password: String): Result<String> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        Result.success(result.user?.uid ?: throw IllegalStateException("User ID is null"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun signIn(email: String, password: String): Result<String> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user?.uid ?: throw IllegalStateException("User ID is null"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun signOut() {
        auth.signOut()
    }

    // Interview session operations
    suspend fun createInterviewSession(session: InterviewSession): Result<String> = try {
        val docRef = firestore.collection("interview_sessions").document()
        val sessionWithId = session.copy(id = docRef.id)
        docRef.set(sessionWithId).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getInterviewSession(sessionId: String): Result<InterviewSession> = try {
        val doc = firestore.collection("interview_sessions").document(sessionId).get().await()
        Result.success(doc.toObject(InterviewSession::class.java) ?: throw IllegalStateException("Session not found"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateInterviewSession(session: InterviewSession): Result<Unit> = try {
        firestore.collection("interview_sessions").document(session.id).set(session).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Chat message operations
    suspend fun sendMessage(message: ChatMessage): Result<String> = try {
        val docRef = firestore.collection("chat_messages").document()
        val messageWithId = message.copy(id = docRef.id)
        docRef.set(messageWithId).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getMessages(sessionId: String): Result<List<ChatMessage>> = try {
        val snapshot = firestore.collection("chat_messages")
            .whereEqualTo("sessionId", sessionId)
            .orderBy("timestamp")
            .get()
            .await()
        Result.success(snapshot.toObjects(ChatMessage::class.java))
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Resume upload
    suspend fun uploadResume(file: File, userId: String): Result<String> = try {
        val storageRef = storage.reference.child("resumes/$userId/${file.name}")
        val uploadTask = storageRef.putFile(Uri.fromFile(file)).await()
        val downloadUrl = uploadTask.storage.downloadUrl.await().toString()
        Result.success(downloadUrl)
    } catch (e: Exception) {
        Result.failure(e)
    }
} 