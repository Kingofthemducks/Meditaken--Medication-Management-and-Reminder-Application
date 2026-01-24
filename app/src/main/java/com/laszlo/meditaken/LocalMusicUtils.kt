package com.laszlo.meditaken

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

/**
 * Returns a list of all music files on the device.
 * Each item is a Pair(displayName, contentUri)
 */
fun getLocalMusicFiles(context: Context): List<Pair<String, Uri>> {
    val musicList = mutableListOf<Pair<String, Uri>>()
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.RELATIVE_PATH // for modern devices
    )

    // Only music files
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.SIZE} > 0"
    val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

    context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        sortOrder
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
        val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val relativePath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                cursor.getString(pathColumn)
            } else {
                ""
            }

            // Optional: Only include files in the Music folder
            if (relativePath.contains("Music", ignoreCase = true) || relativePath.isEmpty()) {
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                musicList.add(name to contentUri)
            }
        }
    }

    return musicList
}
