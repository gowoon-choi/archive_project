package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    val name : String,
    val color : String
) : Parcelable
