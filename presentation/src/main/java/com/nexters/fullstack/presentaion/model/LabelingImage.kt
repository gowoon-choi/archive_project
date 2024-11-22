package com.nexters.fullstack.presentaion.model

import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity

data class LabelingImage(
    val domainLabel: LabelEntity,
    val localImages: List<FileImageEntity>
)
