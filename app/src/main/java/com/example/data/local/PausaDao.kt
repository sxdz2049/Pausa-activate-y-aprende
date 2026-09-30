package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PausaDao {
    // Group Data
    @Query("SELECT * FROM groups_table ORDER BY createdAt DESC LIMIT 1")
    fun getLatestGroup(): Flow<GroupEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity): Long

    // Pretest
    @Query("SELECT * FROM pretest_table ORDER BY timestamp DESC")
    fun getAllPretests(): Flow<List<PretestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPretest(pretest: PretestEntity): Long

    // Postest
    @Query("SELECT * FROM postest_table ORDER BY timestamp DESC")
    fun getAllPostests(): Flow<List<PostestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostest(postest: PostestEntity): Long

    // Pause Records
    @Query("SELECT * FROM pause_records_table ORDER BY timestamp DESC")
    fun getAllPauseRecords(): Flow<List<PauseExecutionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPauseRecord(record: PauseExecutionEntity): Long

    @Query("SELECT COUNT(*) FROM pause_records_table")
    fun getPauseRecordCount(): Flow<Int>

    @Query("DELETE FROM pause_records_table")
    suspend fun clearPauseRecords()

    @Query("DELETE FROM pretest_table")
    suspend fun clearPretests()

    @Query("DELETE FROM postest_table")
    suspend fun clearPostests()
}
