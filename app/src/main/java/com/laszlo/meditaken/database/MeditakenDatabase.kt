package com.laszlo.meditaken.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SupportFactory


//It sets up Room database to store MedicationEntity records
//It encrypts via passphrase using SQLCipher

@Database(entities = [MedicationEntity::class], version = 1)
abstract class MeditakenDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: MeditakenDatabase? = null


    //Ensures only one database instance exists via singleton pattern
    // Allows access to MedicationDao for CRUD operations on medications
        fun getInstance(context: Context, passphrase: ByteArray): MeditakenDatabase {
            return INSTANCE ?: synchronized(this) {
                val factory = SupportFactory(passphrase)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MeditakenDatabase::class.java,
                    "meditaken.db"
                )
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
