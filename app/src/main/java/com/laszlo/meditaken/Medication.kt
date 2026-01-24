package com.laszlo.meditaken

// UI model used for displaying medications in Compose screens
data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String,
    val frequency: Int = 1,  // Default to once per day
    val notes: String? = null // Optional notes
)

// Sample medications for preview/testing purposes
val sampleMedications = listOf(
    Medication("1", "Aspirin", "1 pill", "08:00 AM", frequency = 1, notes = "Take with water"),
    Medication("2", "Vitamin D", "2 pills", "12:00 PM", frequency = 2, notes = "After lunch"),
    Medication("3", "Metformin", "1 pill", "06:00 PM", frequency = 3, notes = "Before dinner")
)
