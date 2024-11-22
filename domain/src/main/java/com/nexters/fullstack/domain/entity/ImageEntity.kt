package com.nexters.fullstack.domain.entity

import java.io.File
import java.io.Serializable
import java.lang.Exception

data class ImageEntity(
    val labels: List<LabelEntity>,
    val image: FileImageEntity,
    val viewCount: Long,
    val liked: Boolean = false,
    var checked: Boolean = false
) : Serializable {

    val createdAt: Long = try {
        File(image.originUrl).lastModified()
    } catch (t: Exception) {
        t.printStackTrace()
        0
    }

    companion object {
        fun empty(): ImageEntity {
            return ImageEntity(emptyList(), FileImageEntity.empty(), 0, false)
        }
    }
}
