package com.nexters.fullstack.util

import android.view.View
import android.view.animation.AnimationUtils
import com.nexters.fullstack.R

fun View.fadeInAnimation() {
    animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation)
}

fun View.fadeOutAnimation() {
    animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation)
}