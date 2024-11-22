package com.nexters.fullstack.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexters.fullstack.databinding.ItemScreenshotBinding
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.viewmodel.HomeListViewModel

class HomeMainChildViewHolder(private val binding : ItemScreenshotBinding, private val mode : HomeListViewModel.SelectMode) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : ImageEntity){
        Glide.with(itemView.context).load(item.image.originUrl).into(binding.ivScreenshot)
        with(item){
            if (liked) binding.ivHeart.visibility = View.VISIBLE
            if (!labels.isNullOrEmpty()) binding.ivLabel.visibility = View.VISIBLE
            if (mode == HomeListViewModel.SelectMode.SELECTION) binding.ivSelect.visibility = View.VISIBLE
            binding.ivSelect.isSelected = item.checked
        }
    }
}