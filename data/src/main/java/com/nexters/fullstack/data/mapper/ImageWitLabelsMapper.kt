package com.nexters.fullstack.data.mapper

import com.nexters.fullstack.data.db.entity.ImageModel
import com.nexters.fullstack.data.db.entity.ImageWithLabels
import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.ImageEntity

object ImageWitLabelsMapper : Mapper<ImageWithLabels, ImageEntity> {
    override fun fromData(data: ImageEntity): ImageWithLabels {
        val labelMapper = data.labels.map(LabelModelMapper::toData)
        val imageMapper = FileImageMapper.toData(data.image)

        return ImageWithLabels(
            image = ImageModel(
                imageMapper.id,
                image = imageMapper,
                viewCount = data.viewCount,
                liked = data.liked,
                createdAt = data.createdAt
            ),
            labels = labelMapper
        )
    }

    override fun toData(data: ImageWithLabels): ImageEntity {
        val labelMapper = data.labels.map(LabelModelMapper::fromData)
        val imageMapper = FileImageMapper.fromData(data.image.image)

        return ImageEntity(labelMapper, imageMapper, data.image.viewCount, data.image.liked)
    }
}