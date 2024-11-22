package com.nexters.fullstack.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LabelEntity(
    val id : Long,
    val color: String,
    val text: String
) : Parcelable