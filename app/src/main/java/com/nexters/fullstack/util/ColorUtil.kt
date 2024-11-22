package com.nexters.fullstack.util

import androidx.core.content.ContextCompat
import com.nexters.fullstack.App
import com.nexters.fullstack.R

class ColorUtil(color: String) {
    private var activeColor : Int = 0
    private var inactiveColor : Int = 0
    private var darkColor : Int = 0
    private var textColor : Int = 0

    init {
        when(color){
            "Yellow" -> {
                activeColor = R.color.yellow_active
                inactiveColor = R.color.yellow_inactive
                darkColor = R.color.yellow_dark
                textColor = R.color.yellow_text
            }
            "Orange" -> {
                activeColor = R.color.orange_active
                inactiveColor = R.color.orange_inactive
                darkColor = R.color.orange_dark
                textColor = R.color.orange_text
            }
            "Red" -> {
                activeColor = R.color.red_active
                inactiveColor = R.color.red_inactive
                darkColor = R.color.red_dark
                textColor = R.color.red_text
            }
            "Pink" -> {
                activeColor = R.color.pink_active
                inactiveColor = R.color.pink_inactive
                darkColor = R.color.pink_dark
                textColor = R.color.pink_text
            }
            "Violet" -> {
                activeColor = R.color.violet_active
                inactiveColor = R.color.violet_inactive
                darkColor = R.color.violet_dark
                textColor = R.color.violet_text
            }
            "Purple Blue" -> {
                activeColor = R.color.cobalt_blue_active
                inactiveColor = R.color.cobalt_blue_inactive
                darkColor = R.color.cobalt_blue_dark
                textColor = R.color.cobalt_blue_text
            }
            "Blue" -> {
                activeColor = R.color.blue_active
                inactiveColor = R.color.blue_inactive
                darkColor = R.color.blue_dark
                textColor = R.color.blue_text
            }
            "Peacock Green" -> {
                activeColor = R.color.peacock_green_active
                inactiveColor = R.color.peacock_green_inactive
                darkColor = R.color.peacock_green_dark
                textColor = R.color.peacock_green_text
            }
            "Green" -> {
                activeColor = R.color.green_active
                inactiveColor = R.color.green_inactive
                darkColor = R.color.green_dark
                textColor = R.color.green_text
            }
            "Gray" -> {
                activeColor = R.color.gray_active
                inactiveColor = R.color.gray_inactive
                darkColor = R.color.gray_dark
                textColor = R.color.gray_text
            }
        }
    }

    fun getActive() : Int = ContextCompat.getColor(App.app, activeColor)
    fun getInactive() : Int = ContextCompat.getColor(App.app, inactiveColor)
    fun getDark() : Int = ContextCompat.getColor(App.app, textColor)
    fun getText() : Int = ContextCompat.getColor(App.app, textColor)
}