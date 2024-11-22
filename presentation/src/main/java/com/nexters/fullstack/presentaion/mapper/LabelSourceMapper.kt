package com.nexters.fullstack.presentaion.mapper

import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.domain.entity.LabelEntity

object LabelSourceMapper : Mapper<List<LabelViewData>, List<LabelEntity>> {
    override fun toData(data: List<LabelViewData>): List<LabelEntity> {
        return data.map {
            LabelEntity(id = it.id, color = it.color, text = it.name)
        }
    }

    override fun fromData(data: List<LabelEntity>): List<LabelViewData> {
        return data.map {
            LabelViewData(id = it.id, color = it.color ?: "", name = it.text)
        }
    }
}