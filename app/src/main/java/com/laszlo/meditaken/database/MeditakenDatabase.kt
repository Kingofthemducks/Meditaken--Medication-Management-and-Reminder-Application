package com.laszlo.meditaken.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SupportFactory

// Sets up the encrypted Room database using SQLCipher
@Database(entities = [MedicationEntity::class], version = 2)
abstract class MeditakenDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: MeditakenDatabase? = null

        // Singleton pattern ensures only one database instance exists
        fun getInstance(context: Context, passphrase: ByteArray): MeditakenDatabase {
            return INSTANCE ?: synchronized(this) {
                val factory = SupportFactory(passphrase)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MeditakenDatabase::class.java,
                    "meditaken.db"
                )
                    // Used during development to handle schema changes
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
