package com.nexters.fullstack.ui.adapter.listener

import com.nexters.fullstack.ui.adapter.source.ItemType

interface BottomSheetClickListener {
    fun onClick(type: ItemType)
}