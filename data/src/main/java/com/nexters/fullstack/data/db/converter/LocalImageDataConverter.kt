package com.nexters.fullstack.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nexters.fullstack.data.model.FileImage

class LocalImageDataConverter {
    @TypeConverter
    fun fromLocalImageData(value: FileImage): String {
        val gson = Gson()

        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToLocalImageData(value: String): FileImage? {
        val type = object : TypeToken<FileImage>() {}.type

        return Gson().fromJson<FileImage>(value, type)
    }
}