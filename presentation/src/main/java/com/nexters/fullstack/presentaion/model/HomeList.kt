package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import com.nexters.fullstack.domain.entity.ImageEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeList(
    val type : HomeListType,
    val title : String = type.title,
    val images : List<ImageEntity>
): Parcelable

