package com.laszlo.meditaken

// UI model used for displaying medications in Compose screens
data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String,
    val frequency: Int = 1,
    val notes: String? = null,
    val days: String = "ALL"
)

// Sample medications for preview
val sampleMedications = listOf(
    Medication("1", "Aspirin", "1 pill", "08:00", frequency = 1, notes = "Take with water", days = "MON,WED,FRI"),
    Medication("2", "Vitamin D", "2 pills", "12:00", frequency = 2, notes = "After lunch", days = "ALL"),
    Medication("3", "Metformin", "1 pill", "18:00", frequency = 3, notes = "Before dinner", days = "TUE,THU")
)
