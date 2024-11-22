package com.nexters.fullstack.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileImage(
    val id: String,
    val originUrl: String,
    val timeStamp: Long = 0
) : Parcelable
