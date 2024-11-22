package com.nexters.fullstack.ui.fragment.home

import android.os.Bundle
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nexters.fullstack.BR
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.databinding.FragmentHomeSearchRecommendBinding
import com.nexters.fullstack.ui.adapter.HomeSearchAdapter
import com.nexters.fullstack.ui.adapter.SelectedLabelAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.presentaion.viewmodel.HomeSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSearchRecommendFragment : BaseFragment<FragmentHomeSearchRecommendBinding, HomeSearchViewModel>() {
    override val layoutRes: Int = R.layout.fragment_home_search_recommend
    override val viewModel: HomeSearchViewModel by viewModel()

    private val selectedLabelAdapter = SelectedLabelAdapter()
    private val recentlySearchLabelAdapter = HomeSearchAdapter()
    private val myLabelAdapter = HomeSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            setVariable(BR.vm, viewModel)
        }
        initView()
        initListener()
        initObserver()
    }

    fun initView(){
        with(binding){
            rvSelectedLabel.adapter = selectedLabelAdapter
            rvRecentlySearch.run {
                adapter = recentlySearchLabelAdapter
                addItemDecoration(SpaceBetweenRecyclerDecoration(RV_SPACING_DP, RV_SPACING_DP))
                layoutManager = FlexboxLayoutManager(this@HomeSearchRecommendFragment.context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                }
                setHasFixedSize(true)
            }
            rvMyLabel.run {
                adapter = myLabelAdapter
                addItemDecoration(SpaceBetweenRecyclerDecoration(RV_SPACING_DP, RV_SPACING_DP))
                layoutManager = FlexboxLayoutManager(this@HomeSearchRecommendFragment.context).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                }
                setHasFixedSize(true)
            }
        }
    }

    fun initListener(){
        myLabelAdapter.setItemClickListener { _, i, _ ->
            viewModel.selectLabel(i, HomeSearchViewModel.ListType.MY_LABEL)
        }
        recentlySearchLabelAdapter.setItemClickListener { _, i, _ ->
            viewModel.selectLabel(i, HomeSearchViewModel.ListType.RECENT_SEARCH_LABEL)
        }
    }

    fun initObserver(){
        with(viewModel.state()){
            recentlySearchLabel.observe(viewLifecycleOwner, {
                recentlySearchLabelAdapter.addItems(it)
            })
            myLabel.observe(viewLifecycleOwner, {
                myLabelAdapter.addItems(it)
            })
            selectedLabel.observe(viewLifecycleOwner, {
                selectedLabelAdapter.addItems(it)
            })
        }
    }

    companion object {
        const val RV_SPACING_DP = 5

        private var instance: HomeSearchRecommendFragment? = null
        fun getInstance(): HomeSearchRecommendFragment {
            if (instance == null) {
                instance = HomeSearchRecommendFragment()
            }
            return instance!!
        }
    }
}