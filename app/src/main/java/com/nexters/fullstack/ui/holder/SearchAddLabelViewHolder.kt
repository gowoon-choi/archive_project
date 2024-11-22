package com.nexters.fullstack.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.databinding.ItemSearchAddBinding
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.Label
import com.nexters.fullstack.presentaion.model.LabelViewData

class SearchAddLabelViewHolder(private val binding : ItemSearchAddBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : LabelViewData){
        binding.tvLabel.text = item.name
    }

    fun bind(item : LabelEntity){
        binding.tvLabel.text = item.text
    }
}