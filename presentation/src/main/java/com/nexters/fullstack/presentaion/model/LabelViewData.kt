package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LabelViewData(
    val id : Long,
    var type: Int = LIST,
    val color: String,
    val name: String,
    var isSelected: Boolean = false
) : Parcelable {
    companion object {
        const val DEFAULT = 0
        const val RECOMMEND = 1
        const val SELECTED = 2
        const val LIST = 3
    }
}