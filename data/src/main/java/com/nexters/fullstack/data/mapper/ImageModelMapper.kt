package com.nexters.fullstack.data.mapper

import com.nexters.fullstack.data.db.entity.ImageModel
import com.nexters.fullstack.data.db.entity.ImageWithLabels
import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.ImageEntity

object ImageModelMapper : Mapper<ImageModel, ImageEntity> {
    override fun fromData(data: ImageEntity): ImageModel {
        return ImageModel(
            data.image.id,
            FileImageMapper.toData(data.image),
            data.viewCount,
            data.createdAt,
            data.liked
        )
    }

    override fun toData(data: ImageModel): ImageEntity {
        return ImageEntity(
            emptyList(),
            FileImageMapper.fromData(data.image),
            data.viewCount,
            data.liked
        )
    }
}