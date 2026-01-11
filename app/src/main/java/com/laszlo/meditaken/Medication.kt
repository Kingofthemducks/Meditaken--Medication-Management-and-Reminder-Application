package com.laszlo.meditaken

data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String
)

val sampleMedications = listOf(
    Medication("1", "Aspirin", "1 pill", "08:00 AM"),
    Medication("2", "Vitamin D", "2 pills", "12:00 PM"),
    Medication("3", "Metformin", "1 pill", "06:00 PM")
)
