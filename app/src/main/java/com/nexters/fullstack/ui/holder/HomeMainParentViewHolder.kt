package com.nexters.fullstack.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.databinding.ItemHomeGroupBinding
import com.nexters.fullstack.presentaion.model.HomeList
import com.nexters.fullstack.ui.adapter.HomeMainChildAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration

class HomeMainParentViewHolder(private val binding : ItemHomeGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : HomeList){
        val childAdapter = HomeMainChildAdapter(item.type)
        childAdapter.addItems(item.images)
        binding.tvTitle.text = item.title
        with(binding.rvImage){
            adapter = childAdapter
            addItemDecoration(SpaceBetweenRecyclerDecoration(horizontal = RV_HORIZONTAL_MARGIN))

        }
    }

    companion object {
        const val RV_HORIZONTAL_MARGIN = 3
    }
}