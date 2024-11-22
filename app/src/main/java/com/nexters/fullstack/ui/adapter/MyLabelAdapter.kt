package com.nexters.fullstack.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.util.NotFoundViewType
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.databinding.ItemLabelBinding
import com.nexters.fullstack.databinding.ItemListLabelBinding
import com.nexters.fullstack.databinding.ItemLocalSearchViewBinding
import com.nexters.fullstack.databinding.ItemSelectedLabelBinding
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.ui.holder.*

class MyLabelAdapter(private val isSearchViewHolder: Boolean = false) : BaseAdapter<LabelViewData>() {
    private val _selectedLabel = mutableListOf<LabelViewData>()
    val selectedLabel: List<LabelViewData>
        get() = _selectedLabel
    lateinit var finish: (LabelViewData) -> Unit

    lateinit var callback: (MutableList<LabelViewData>) -> Unit

    private val onLabelClickListener = { position: Int ->
        val getLabelSource = getItem(position)
        if (_selectedLabel.contains(getLabelSource)) {
            _selectedLabel.remove(getLabelSource)
        } else {
            _selectedLabel.add(getLabelSource)
        }
        callback(_selectedLabel)
        notifyDataSetChanged()
    }

    private val onSearchLabelClickListener = { position: Int ->
        val getLabelSource = getItem(position)
        _selectedLabel.add(getLabelSource)
        finish(_selectedLabel.first())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            LabelViewData.RECOMMEND -> RecommendLabelViewHolder(
                ItemLabelBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            LabelViewData.LIST ->
                if (isSearchViewHolder) {
                    SearchLocalListViewHolder(
                        onSearchLabelClickListener,
                        ItemLocalSearchViewBinding.inflate(LayoutInflater.from(parent.context))
                    )
                } else {
                    LabelListViewHolder(
                        onLabelClickListener,
                        ItemListLabelBinding.inflate(LayoutInflater.from(parent.context))
                    )
                }

            LabelViewData.SELECTED -> {
                LabelingSelectedViewHolder(
                    ItemSelectedLabelBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }
            else -> throw NotFoundViewType
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecommendLabelViewHolder -> holder.bind(items[position])
            is LabelListViewHolder -> {
                holder.bind(_selectedLabel, items[position])
            }
            is SearchLocalListViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int) = items[position].type

    fun selectedLabelClear() {
        _selectedLabel.clear()
    }

    fun addSelectedItem(item: LabelViewData) {
        if (_selectedLabel.contains(item)) {
            _selectedLabel.remove(item)
        } else {
            _selectedLabel.add(item)
        }
    }
}