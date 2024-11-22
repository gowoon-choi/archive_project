package com.nexters.fullstack.ui.activity.home

import android.os.Bundle
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityHomeSearchResultBinding
import com.nexters.fullstack.presentaion.viewmodel.HomeSearchResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSearchResultActivity : BaseActivity<ActivityHomeSearchResultBinding, HomeSearchResultViewModel>() {
    override val layoutRes: Int = R.layout.activity_home_search_result
    override val viewModel: HomeSearchResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
//            it.vm = viewModel
        }
    }
}
