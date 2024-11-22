package com.nexters.fullstack.ui.fragment.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.ui.adapter.HomeSearchAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.ui.fragment.home.HomeSearchRecommendFragment

object HomeSearchBindingAdapter {
    @JvmStatic
    @BindingAdapter("bind:searchResultList")
    fun RecyclerView.setSearchResultList(items: List<LabelEntity>?) {
        adapter?.let {
            if (it is HomeSearchAdapter) {
                items?.let(it::addItems)
            }
        } ?: run {
            val searchResultAdapter = HomeSearchAdapter()

            adapter = searchResultAdapter

            addItemDecoration(
                SpaceBetweenRecyclerDecoration(
                    HomeSearchRecommendFragment.RV_SPACING_DP,
                    HomeSearchRecommendFragment.RV_SPACING_DP
                )
            )
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            setHasFixedSize(true)
        }
    }
}