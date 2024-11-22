package com.nexters.fullstack.ui.adapter.listener

import com.nexters.fullstack.domain.entity.LabelEntity

interface ItemClickListener {
    fun onClick(item: LabelEntity)
}