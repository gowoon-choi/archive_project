package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileImageViewData(val url: String, val timeStamp: Long) : Parcelable
