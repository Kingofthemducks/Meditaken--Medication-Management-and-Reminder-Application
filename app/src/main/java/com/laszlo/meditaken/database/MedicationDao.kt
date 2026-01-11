package com.laszlo.meditaken.database

import androidx.room.*

@Dao
interface MedicationDao {

    //Handles adding and updating medications with insert generating and returning IDS
    // Update changes exisitng medications
    @Insert
    suspend fun insert(med: MedicationEntity): Long

    @Update
    suspend fun update(med: MedicationEntity)


    // Retrieves medications from the database with getAll returning all medications
    //getById fetches med records by ID



    @Query("SELECT * FROM medications")
    suspend fun getAll(): List<MedicationEntity>

    @Query("SELECT * FROM medications WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): MedicationEntity?
}

