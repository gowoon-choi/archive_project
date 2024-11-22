package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Screenshot (
    val imageUrl : String,
    val labels : List<Label>?,
    val isFavorite : Boolean,
    var isChecked : Boolean = false
) : Parcelable
