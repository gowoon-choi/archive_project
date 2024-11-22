package com.nexters.fullstack.presentaion.mapper

import com.nexters.feature.ui.data.pallet.PalletItem
import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.presentaion.model.MainMakeLabelSource
import com.nexters.fullstack.domain.entity.LabelEntity

object LabelingMapper : Mapper<LabelEntity, MainMakeLabelSource> {
    override fun toData(data: LabelEntity): MainMakeLabelSource {
        return MainMakeLabelSource(data.text, PalletItem(""))
    }

    override fun fromData(data: MainMakeLabelSource): LabelEntity {
        return LabelEntity(-1L, data.palletItem.name, data.labelText)
    }
}