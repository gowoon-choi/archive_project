package com.nexters.fullstack.ui.holder

import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.databinding.ItemLabelBinding
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.util.ColorUtil

class MyLabelViewHolder(private val binding: ItemLabelBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item : LabelEntity){
        val colorUtil = ColorUtil(item.color ?: "")
        binding.tvLabel.text = item.text
        DrawableCompat.setTint(binding.container.background, colorUtil.getInactive())
        binding.tvLabel.setTextColor(colorUtil.getText())
    }
}