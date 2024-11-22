package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.LabelViewType
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.databinding.ItemStackViewBinding
import com.nexters.fullstack.presentaion.model.FileImageViewData
import com.nexters.fullstack.ui.holder.MainStackItemHolder

class MainStackAdapter : BaseAdapter<FileImageViewData>() {
    var isSwipe = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LabelViewType.STACK_VIEW -> {
                MainStackItemHolder(
                    ItemStackViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                )
            }
            else -> throw NotFoundViewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainStackItemHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return LabelViewType.STACK_VIEW
    }
}