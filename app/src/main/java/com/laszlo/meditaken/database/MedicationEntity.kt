package com.laszlo.meditaken.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val dosage: String,
    val time: String,
    val frequency: Int,
    val notes: String?,
    val days: String = "ALL"
)
