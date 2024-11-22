package com.nexters.fullstack.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nexters.fullstack.data.db.TableName
import com.nexters.fullstack.data.model.FileImage
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TableName.IMAGE)
data class ImageModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imageId")
    var imageId: String,
    @ColumnInfo(name = "image")
    val image: FileImage,
    @ColumnInfo(name = "viewCount")
    val viewCount : Long,
    @ColumnInfo(name = "createdAt")
    val createdAt : Long,
    @ColumnInfo(name = "liked")
    var liked: Boolean = false
) : Parcelable
