package com.laszlo.meditaken.database

import androidx.room.Entity
import androidx.room.PrimaryKey



//Represents and generates ID for each medical record making sure each record is unique
//Its for Room to map between objects and database rows
@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var dosage: String,
    var time: String
)
