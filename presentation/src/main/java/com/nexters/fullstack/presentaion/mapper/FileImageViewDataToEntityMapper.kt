package com.nexters.fullstack.presentaion.mapper

import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.ImageEntity

object FileImageViewDataToEntityMapper : Mapper<FileImageEntity, ImageEntity> {
    override fun toData(data: FileImageEntity): ImageEntity {
        return ImageEntity(emptyList(), data, 0)
    }

    override fun fromData(data: ImageEntity): FileImageEntity {
        return data.image
    }
}