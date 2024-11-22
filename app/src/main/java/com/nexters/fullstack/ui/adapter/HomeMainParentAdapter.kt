package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.presentaion.model.HomeList
import com.nexters.fullstack.ui.holder.HomeMainParentViewHolder
import com.nexters.fullstack.ui.holder.HomeMainSearchViewHolder

class HomeMainParentAdapter : BaseAdapter<HomeList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            SEARCH -> HomeMainSearchViewHolder(
                DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_home_search,
                        parent,
                        false
                    )
            )
            DEFAULT -> HomeMainParentViewHolder(
                DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_home_group,
                        parent,
                        false
                    )
            )
            else -> throw NotFoundViewType
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HomeMainSearchViewHolder -> {
                holder.bind()
                holder.itemView.setOnClickListener {
                    getItemClickListener()?.invoke(
                        it,
                        0,
                        null
                    )
                }
            }
            is HomeMainParentViewHolder -> {
                holder.bind(items[position-1])
                holder.itemView.setOnClickListener {
                    getItemClickListener()?.invoke(
                        it, position, items[position-1]
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) SEARCH
        else DEFAULT
    }

    override fun getItemCount(): Int {
        return if(items.isNullOrEmpty()) 1
        else super.getItemCount()+1
    }

    companion object{
        const val SEARCH = 0
        const val DEFAULT = 1
    }

}