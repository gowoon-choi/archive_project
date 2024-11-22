package com.nexters.fullstack.ui.widget.bottomsheet.mapper

import com.nexters.feature.ui.data.pallet.PalletItem
import com.nexters.fullstack.data.db.entity.LabelModel
import com.nexters.fullstack.domain.entity.LabelEntity

fun LabelEntity.mapToPallet(): PalletItem {
    return PalletItem(name = color)
}