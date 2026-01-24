package com.laszlo.meditaken.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Represents a medication record stored in the encrypted Room database
// Supports scheduling frequency and optional reminder notes
@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var dosage: String,
    var time: String,
    var frequency: Int = 1,      // Number of times per day (e.g. 1, 2, 3)
    var notes: String? = null    // Optional notes shown with alarm

)
