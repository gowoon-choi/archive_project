package com.nexters.fullstack.util.extension

import android.util.TypedValue
import com.nexters.fullstack.App

val Int.toPx: Int
    get() {
        val metrics = App.app.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }