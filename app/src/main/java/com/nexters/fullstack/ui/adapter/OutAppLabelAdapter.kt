package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.databinding.ItemEmptyLabelBinding
import com.nexters.fullstack.databinding.ItemLabelBinding
import com.nexters.fullstack.databinding.ItemSearchAddBinding
import com.nexters.fullstack.databinding.ItemTitleCountBinding
import com.nexters.fullstack.ui.holder.EmptyLabelViewHolder
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.ui.holder.MyLabelViewHolder
import com.nexters.fullstack.ui.holder.SearchAddLabelViewHolder
import com.nexters.fullstack.ui.holder.TitleViewHolder
import com.nexters.fullstack.presentaion.viewmodel.LabelOutAppViewModel

class OutAppLabelAdapter(private val state : LabelOutAppViewModel.ViewState) : BaseAdapter<LabelEntity>() {
    var text = when (state){
        LabelOutAppViewModel.ViewState.MY_LABEL -> MY_LABEL_TITLE
        LabelOutAppViewModel.ViewState.RECENT_LABEL -> RECENT_SEARCH_TITLE
        LabelOutAppViewModel.ViewState.SEARCH_RESULT -> SEARCH_RESULT_TITLE
        LabelOutAppViewModel.ViewState.NO_RESULT -> NO_SEARCH_RESULT
        else -> ""
    }

    var isShowCount = when(state){
        LabelOutAppViewModel.ViewState.MY_LABEL -> true
        LabelOutAppViewModel.ViewState.RECENT_LABEL -> false
        LabelOutAppViewModel.ViewState.SEARCH_RESULT -> true
        LabelOutAppViewModel.ViewState.NO_RESULT -> false
        else -> false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            EMPTY -> EmptyLabelViewHolder(ItemEmptyLabelBinding.inflate(
                LayoutInflater.from(parent.context)
            ))
            ADD_LABEL -> SearchAddLabelViewHolder(
                ItemSearchAddBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            TITLE -> TitleViewHolder(
                ItemTitleCountBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            LabelViewData.DEFAULT -> MyLabelViewHolder(
                ItemLabelBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            else -> throw NotFoundViewType
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MyLabelViewHolder -> {
                holder.bind(items[position-1])
                holder.itemView.setOnClickListener {
                    getItemClickListener()?.invoke(
                        it,
                        holder.adapterPosition-1,
                        items[holder.adapterPosition-1]
                    )
                }
            }
            is TitleViewHolder -> {
                if(isShowCount) holder.bind(text, itemCount-1)
                else holder.bind(text)
            }
            is SearchAddLabelViewHolder -> {
                holder.bind(items[position-1])
                holder.itemView.setOnClickListener {
                    getItemClickListener()?.invoke(
                        it, holder.adapterPosition-1, items[holder.adapterPosition-1]
                    )
                }
            }
            is EmptyLabelViewHolder -> {
                holder.bind()
                holder._binding.tvAddLabel.setOnClickListener {
                    getItemClickListener()?.invoke(it, -1, null)
                }
            }
        }
    }

    override fun getItemViewType(position: Int) : Int {
        return if(state == LabelOutAppViewModel.ViewState.NO_LABEL) EMPTY
        else if(position == 0) TITLE
        else{
            if(state == LabelOutAppViewModel.ViewState.NO_RESULT) ADD_LABEL
            else LabelViewData.DEFAULT
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }

    companion object{
        const val TITLE = 2000
        const val ADD_LABEL = 2001
        const val EMPTY = 2002

        const val MY_LABEL_TITLE = "라벨 목록"
        const val RECENT_SEARCH_TITLE = "최근 검색한 라벨"
        const val SEARCH_RESULT_TITLE = "검색결과"
        const val NO_SEARCH_RESULT = "검색 결과가 없습니다."
    }
}