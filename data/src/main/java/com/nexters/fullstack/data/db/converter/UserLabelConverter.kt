package com.nexters.fullstack.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nexters.fullstack.data.db.entity.LabelModel

class UserLabelConverter {
    @TypeConverter
    fun jsonToUserLabel(value: String): List<LabelModel>? {
        val userLabelType = object : TypeToken<List<LabelModel>>() {}.type

        return Gson().fromJson<List<LabelModel>>(value, userLabelType)
    }

    @TypeConverter
    fun fromUserLabel(value: List<LabelModel>): String {
        val gson = Gson()

        return gson.toJson(value)
    }
}