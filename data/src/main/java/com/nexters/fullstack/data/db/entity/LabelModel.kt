package com.nexters.fullstack.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nexters.fullstack.data.db.TableName

@Entity(tableName = TableName.LABEL)
data class LabelModel(
    @PrimaryKey(autoGenerate = true)
    var labelId: Long = 0L,

    @ColumnInfo(name = "color")
    val color: String,

    @ColumnInfo(name = "text")
    val text: String,
)
