package com.nexters.fullstack.data.local.filesystem

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import io.reactivex.Completable
import android.content.ContentUris
import android.content.ContentResolver




internal class FileSystemImagesImpl(private val context: Context) : FileSystemImages {
    private val result = arrayListOf<String>()

    @SuppressLint("Recycle")
    @Suppress("DEPRECATION")
    override fun delete(path: String): Completable {

        return Completable.create { emitter ->

            val projection = listOf(MediaStore.Images.Media._ID)

            val selection = MediaStore.MediaColumns.DATA + " = ?"
            val selectionArgs = listOf(path)

            val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val contentResolver: ContentResolver = context.contentResolver

            val c = contentResolver.query(queryUri, projection.toTypedArray(), selection, selectionArgs.toTypedArray(),          MediaStore.MediaColumns.DATE_ADDED + " desc")

            c.use {
                if(c?.moveToFirst() == true) {
                    val id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val deleteUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    contentResolver.delete(deleteUri, null, null)
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable("not found file"))
                }
            }

        }
    }

    @SuppressLint("Recycle")
    @Suppress("DEPRECATION")
    override fun fetch(filterValue: String): ArrayList<String> {

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)

        val cursor: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            MediaStore.MediaColumns.DATE_ADDED + " desc"
        )

        val columnIndex: Int = cursor?.getColumnIndexOrThrow("_data") ?: 0

        while (cursor?.moveToNext() == true) {
            val absolutePathOfImage: String = cursor.getString(columnIndex)


            val isLocalScreenShot = absolutePathOfImage.contains(filterValue)

            if (!TextUtils.isEmpty(absolutePathOfImage) && isLocalScreenShot) {
                result.add(absolutePathOfImage)
            }
        }
        return result
    }

    override fun getSize(): Int {
        return result.size
    }
}