package com.nexters.fullstack.ui.holder

import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.databinding.ItemSelectedLabelBinding
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.Label
import com.nexters.fullstack.util.ColorUtil

class SelectedLabelViewHolder(private val binding : ItemSelectedLabelBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : LabelEntity){
        val colorUtil = ColorUtil(item.color ?: "")
        binding.tvLabel.text = item.text
        DrawableCompat.setTint(binding.container.background, colorUtil.getInactive())
        binding.tvLabel.setTextColor(colorUtil.getActive())
        binding.ivDelete.setColorFilter(colorUtil.getActive())
    }
}