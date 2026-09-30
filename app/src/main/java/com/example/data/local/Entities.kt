package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups_table")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teacherName: String,
    val grade: String,
    val subject: String,
    val studentCount: Int,
    val approxAge: String,
    val date: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "pretest_table")
data class PretestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentCode: String, // e.g., "EST-001"
    val q1: Int,
    val q2: Int,
    val q3: Int,
    val q4: Int,
    val q5: Int,
    val q6: Int,
    val q7: Int,
    val q8: Int,
    val q9: Int,
    val q10: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "postest_table")
data class PostestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentCode: String, // e.g., "EST-001"
    val q1: Int,
    val q2: Int,
    val q3: Int,
    val q4: Int,
    val q5: Int,
    val q6: Int,
    val q7: Int,
    val q8: Int,
    val q9: Int,
    val q10: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "pause_records_table")
data class PauseExecutionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pauseId: Int,
    val pauseTitle: String,
    val categoryName: String,
    val dateString: String,
    val moodRating: String, // "Muy bien", "Bien", "Regular", "Poco"
    val attentionLevel: String, // "Baja", "Media", "Alta"
    val motivationLevel: String,
    val participationLevel: String,
    val dispositionLevel: String,
    val observations: String,
    val durationSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)
