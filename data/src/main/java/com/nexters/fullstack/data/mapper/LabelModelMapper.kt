package com.nexters.fullstack.data.mapper

import com.nexters.fullstack.data.db.entity.LabelModel
import com.nexters.fullstack.data.db.entity.LabelWithImages
import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity

object LabelModelMapper : Mapper<LabelEntity, LabelModel> {
    override fun toData(data: LabelEntity): LabelModel {
        return LabelModel(labelId = data.id.takeIf { it != -1L } ?: 0, color = data.color ?: "", text = data.text)
    }

    override fun fromData(data: LabelModel): LabelEntity {
        return LabelEntity(
            data.labelId,
            data.color,
            data.text
        )
    }
}