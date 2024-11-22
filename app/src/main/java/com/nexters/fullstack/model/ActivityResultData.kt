package com.nexters.fullstack.model

import android.content.Intent

data class ActivityResultData(
    val requestCode: Int = 0,
    val resultCode: Int = 0,
    val data: Intent? = null,
    val result: Any? = null
)