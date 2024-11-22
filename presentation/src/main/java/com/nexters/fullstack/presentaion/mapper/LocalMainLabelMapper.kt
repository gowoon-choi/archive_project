package com.nexters.fullstack.presentaion.mapper

import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.LabelViewData


object LocalMainLabelMapper : Mapper<LabelViewData, LabelEntity> {
    override fun fromData(data: LabelEntity): LabelViewData {
        return LabelViewData(id = data.id, color = data.color ?: "", name = data.text)
    }

    override fun toData(data: LabelViewData): LabelEntity {
        return LabelEntity(data.id,data.color, data.name)
    }
}
