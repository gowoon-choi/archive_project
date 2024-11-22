package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.model.HomeListType
import com.nexters.fullstack.presentaion.viewmodel.HomeListViewModel
import com.nexters.fullstack.ui.holder.HomeMainChildEmptyFavoriteViewHolder
import com.nexters.fullstack.ui.holder.HomeMainChildEmptyRecentViewHolder
import com.nexters.fullstack.ui.holder.HomeMainChildViewHolder

class HomeMainChildAdapter(private val type : HomeListType) : BaseAdapter<ImageEntity>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        when(viewType){
            DEFAULT -> {
                HomeMainChildViewHolder(DataBindingUtil
                    .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_screenshot,
                        parent,
                        false
                    ),
                    HomeListViewModel.SelectMode.DEFAULT
                )
            }
            EMPTY -> {
                when (type) {
                    HomeListType.RECENT -> HomeMainChildEmptyRecentViewHolder(
                        DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_home_empty_recent,
                            parent,
                            false
                        )
                    )
                    HomeListType.FAVORITE -> HomeMainChildEmptyFavoriteViewHolder(
                        DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_home_empty_favorite,
                            parent,
                            false
                        )
                    )
                    else -> throw NotFoundViewType
                }
            }
            else -> throw NotFoundViewType
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HomeMainChildViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    getItemClickListener()?.invoke(
                        it, position, items[position]
                    )
                }
            }
            is HomeMainChildEmptyRecentViewHolder -> {

            }
            is HomeMainChildEmptyFavoriteViewHolder -> {

            }
        }
    }

    override fun getItemCount(): Int {
        if(items.isNullOrEmpty()) return super.getItemCount() + 1
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if(items.isNullOrEmpty()) EMPTY else DEFAULT
    }

    companion object{
        const val DEFAULT = 0
        const val EMPTY = 1
    }

}
