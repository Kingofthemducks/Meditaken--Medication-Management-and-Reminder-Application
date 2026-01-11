package com.laszlo.meditaken.database

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.SecureRandom


object KeyStoreManager {
    private const val PREFS_NAME = "meditaken_prefs"
    private const val KEY_DATABASE_PASSPHRASE = "db_passphrase"







    // Retrieves a secure 32-byte key for encrypting the database because SQLCipher expects 256-bit keys,
    // either by decoding a previously stored key or generating a new random one if none exists.

    fun getDatabaseKey(context: Context): ByteArray {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val prefs = EncryptedSharedPreferences.create(
            PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    //The key is stored securely in EncryptedSharedPreferences using a master key,
    //and ensures the key is not stored in plain text

        val passphraseBase64 = prefs.getString(KEY_DATABASE_PASSPHRASE, null)
        val keyBytes = if (passphraseBase64 != null) {
            Base64.decode(passphraseBase64, Base64.NO_WRAP)
        } else {
            val newKey = ByteArray(32)
            SecureRandom().nextBytes(newKey)
            prefs.edit().putString(KEY_DATABASE_PASSPHRASE, Base64.encodeToString(newKey, Base64.NO_WRAP)).apply()
            newKey
        }

        Log.d("KeyStoreManager", "DB key: ${keyBytes.joinToString("") { "%02x".format(it) }}")
        return keyBytes
    }

}
