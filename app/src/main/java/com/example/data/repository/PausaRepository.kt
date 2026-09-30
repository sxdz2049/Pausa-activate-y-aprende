package com.example.data.repository

import com.example.data.local.GroupEntity
import com.example.data.local.PauseExecutionEntity
import com.example.data.local.PausaDao
import com.example.data.local.PostestEntity
import com.example.data.local.PretestEntity
import com.example.data.model.AssessmentComparison
import com.example.data.model.GroupInfo
import com.example.data.model.PauseRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PausaRepository(private val dao: PausaDao) {

    val latestGroup: Flow<GroupInfo?> = dao.getLatestGroup().map { entity ->
        entity?.let {
            GroupInfo(
                teacherName = it.teacherName,
                grade = it.grade,
                subject = it.subject,
                studentCount = it.studentCount,
                approxAge = it.approxAge,
                date = it.date
            )
        }
    }

    val pretestList: Flow<List<PretestEntity>> = dao.getAllPretests()
    val postestList: Flow<List<PostestEntity>> = dao.getAllPostests()

    val pauseRecords: Flow<List<PauseRecord>> = dao.getAllPauseRecords().map { entities ->
        entities.map {
            PauseRecord(
                id = it.id,
                pauseId = it.pauseId,
                pauseName = it.pauseTitle,
                categoryName = it.categoryName,
                timestamp = it.timestamp,
                formattedDate = it.dateString,
                groupReaction = it.moodRating,
                attentionLevel = it.attentionLevel,
                motivationLevel = it.motivationLevel,
                participationLevel = it.participationLevel,
                dispositionLevel = it.dispositionLevel,
                observations = it.observations
            )
        }
    }

    val pauseCount: Flow<Int> = dao.getPauseRecordCount()

    suspend fun saveGroup(group: GroupInfo) {
        dao.insertGroup(
            GroupEntity(
                teacherName = group.teacherName,
                grade = group.grade,
                subject = group.subject,
                studentCount = group.studentCount,
                approxAge = group.approxAge,
                date = group.date
            )
        )
    }

    suspend fun submitPretest(
        studentCode: String,
        answers: Map<Int, Int> // 1..10 to 1..4
    ) {
        val entity = PretestEntity(
            studentCode = studentCode,
            q1 = answers[1] ?: 3,
            q2 = answers[2] ?: 3,
            q3 = answers[3] ?: 3,
            q4 = answers[4] ?: 3,
            q5 = answers[5] ?: 3,
            q6 = answers[6] ?: 3,
            q7 = answers[7] ?: 3,
            q8 = answers[8] ?: 3,
            q9 = answers[9] ?: 3,
            q10 = answers[10] ?: 3
        )
        dao.insertPretest(entity)
    }

    suspend fun submitPostest(
        studentCode: String,
        answers: Map<Int, Int> // 1..10 to 1..4
    ) {
        val entity = PostestEntity(
            studentCode = studentCode,
            q1 = answers[1] ?: 3,
            q2 = answers[2] ?: 3,
            q3 = answers[3] ?: 3,
            q4 = answers[4] ?: 3,
            q5 = answers[5] ?: 3,
            q6 = answers[6] ?: 3,
            q7 = answers[7] ?: 3,
            q8 = answers[8] ?: 3,
            q9 = answers[9] ?: 3,
            q10 = answers[10] ?: 3
        )
        dao.insertPostest(entity)
    }

    suspend fun savePauseExecution(
        pauseId: Int,
        title: String,
        category: String,
        mood: String,
        attention: String,
        motivation: String,
        participation: String,
        disposition: String,
        observations: String,
        durationSeconds: Int
    ) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val dateStr = dateFormat.format(Date())
        val entity = PauseExecutionEntity(
            pauseId = pauseId,
            pauseTitle = title,
            categoryName = category,
            dateString = dateStr,
            moodRating = mood,
            attentionLevel = attention,
            motivationLevel = motivation,
            participationLevel = participation,
            dispositionLevel = disposition,
            observations = observations,
            durationSeconds = durationSeconds
        )
        dao.insertPauseRecord(entity)
    }

    suspend fun seedDemoClassroomData() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        // Seed 5 sample pause records
        val samplePauses = listOf(
            Triple(1, "Semáforo corporal", "Atención"),
            Triple(6, "Respira y activa", "Regulación"),
            Triple(3, "Sigue el ritmo", "Ritmo"),
            Triple(2, "Espejo", "Cooperación"),
            Triple(10, "Animal en movimiento", "Movimiento")
        )

        for ((idx, pause) in samplePauses.withIndex()) {
            val daysAgo = (5 - idx) * 86400000L
            dao.insertPauseRecord(
                PauseExecutionEntity(
                    pauseId = pause.first,
                    pauseTitle = pause.second,
                    categoryName = pause.third,
                    dateString = dateFormat.format(Date(System.currentTimeMillis() - daysAgo)),
                    moodRating = if (idx % 2 == 0) "Muy bien" else "Bien",
                    attentionLevel = if (idx >= 3) "Alta" else "Media",
                    motivationLevel = "Alta",
                    participationLevel = "Alta",
                    dispositionLevel = "Alta",
                    observations = "Excelente receptividad del grupo. Disminución de bostezos y mayor agilidad al retomar la clase.",
                    durationSeconds = 180,
                    timestamp = System.currentTimeMillis() - daysAgo
                )
            )
        }

        // Seed 6 anonymous pretests
        val pretestData = listOf(
            intArrayOf(2, 2, 2, 2, 4, 4, 3, 2, 3, 3), // EST-001
            intArrayOf(3, 3, 2, 3, 3, 3, 4, 3, 3, 2), // EST-002
            intArrayOf(2, 2, 3, 2, 4, 4, 2, 2, 2, 3), // EST-003
            intArrayOf(3, 2, 2, 2, 3, 4, 3, 3, 3, 2), // EST-004
            intArrayOf(2, 3, 3, 3, 3, 3, 4, 3, 3, 3), // EST-005
            intArrayOf(3, 3, 2, 2, 4, 3, 3, 2, 2, 3)  // EST-006
        )

        for ((idx, arr) in pretestData.withIndex()) {
            dao.insertPretest(
                PretestEntity(
                    studentCode = "EST-${String.format(Locale.US, "%03d", idx + 1)}",
                    q1 = arr[0], q2 = arr[1], q3 = arr[2], q4 = arr[3], q5 = arr[4],
                    q6 = arr[5], q7 = arr[6], q8 = arr[7], q9 = arr[8], q10 = arr[9]
                )
            )
        }

        // Seed 6 matched posttests showing positive trend
        val postestData = listOf(
            intArrayOf(3, 4, 3, 4, 2, 2, 4, 4, 4, 4), // EST-001
            intArrayOf(4, 4, 3, 3, 2, 2, 4, 4, 4, 3), // EST-002
            intArrayOf(3, 3, 4, 3, 2, 1, 3, 3, 4, 4), // EST-003
            intArrayOf(4, 3, 3, 4, 1, 2, 4, 4, 3, 3), // EST-004
            intArrayOf(3, 4, 4, 4, 2, 2, 4, 4, 4, 4), // EST-005
            intArrayOf(4, 4, 3, 3, 1, 2, 4, 3, 3, 4)  // EST-006
        )

        for ((idx, arr) in postestData.withIndex()) {
            dao.insertPostest(
                PostestEntity(
                    studentCode = "EST-${String.format(Locale.US, "%03d", idx + 1)}",
                    q1 = arr[0], q2 = arr[1], q3 = arr[2], q4 = arr[3], q5 = arr[4],
                    q6 = arr[5], q7 = arr[6], q8 = arr[7], q9 = arr[8], q10 = arr[9]
                )
            )
        }
    }

    suspend fun clearDatabase() {
        dao.clearPauseRecords()
        dao.clearPretests()
        dao.clearPostests()
    }
}
