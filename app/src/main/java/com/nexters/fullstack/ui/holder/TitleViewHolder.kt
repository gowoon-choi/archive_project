package com.nexters.fullstack.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.databinding.ItemTitleCountBinding

class TitleViewHolder(private val binding: ItemTitleCountBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(text : String){
        binding.tvTitle.text = text
    }

    fun bind(text : String, count : Int){
        binding.tvTitle.text = text
        binding.tvCount.text = count.toString()
    }
}