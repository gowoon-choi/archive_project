package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.BR
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.databinding.ItemBottomSheetLabelDeleteBinding
import com.nexters.fullstack.databinding.ItemBottomSheetLabelUpdateBinding
import com.nexters.fullstack.presentaion.model.bottomsheet.BottomSheetItem

class BottomSheetAdapter(private val event: Any? = null) : BaseAdapter<BottomSheetItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            7000 -> {
                LabelUpdateViewHolder(
                    ItemBottomSheetLabelUpdateBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }
            7001 -> {
                LabelDeleteViewHolder(
                    ItemBottomSheetLabelDeleteBinding.inflate(
                        LayoutInflater.from(parent.context)
                    )
                )
            }
            else -> throw NotFoundViewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LabelDeleteViewHolder -> {
                holder.bind(items[position])
            }
            is LabelUpdateViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    private inner class LabelUpdateViewHolder(
        private val binding: ItemBottomSheetLabelUpdateBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bindItem: BottomSheetItem) {
            binding.item = bindItem
            binding.setVariable(BR.onClickEvent, event)
            binding.executePendingBindings()
        }

    }

    private inner class LabelDeleteViewHolder(private val binding: ItemBottomSheetLabelDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bindItem: BottomSheetItem) {
            binding.item = bindItem
            binding.setVariable(BR.onClickEvent, event)
            binding.executePendingBindings()
        }
    }

}