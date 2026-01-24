package com.laszlo.meditaken.database

import androidx.room.*

@Dao
interface MedicationDao {

    @Insert
    suspend fun insert(med: MedicationEntity): Long

    @Update
    suspend fun update(med: MedicationEntity)

    @Query("SELECT * FROM medications")
    suspend fun getAll(): List<MedicationEntity>

    @Query("SELECT * FROM medications WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): MedicationEntity?
}
