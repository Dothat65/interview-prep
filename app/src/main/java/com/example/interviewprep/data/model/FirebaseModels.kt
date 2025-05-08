package com.example.interviewprep.data.model

import com.google.firebase.Timestamp

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val ageGroup: String = "",
    val gender: String = "",
    val jobField: String = "",
    val profileImageUrl: String = "",
    val resumeUrl: String = "",
    val mockPoints: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)

// Firebase Collection Names
object FirebaseCollections {
    const val USERS = "users"
    const val INTERVIEWS = "interviews"
}

// Firebase Storage Paths
object FirebaseStorage {
    const val PROFILE_IMAGES = "profileImages"
    const val RESUMES = "resumes"

    fun getProfileImagePath(uid: String) = "$PROFILE_IMAGES/$uid.jpg"
    fun getResumePath(uid: String) = "$RESUMES/$uid/resume.pdf"
} 