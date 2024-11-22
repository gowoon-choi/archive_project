package com.nexters.fullstack.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import com.nexters.fullstack.BR
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.R
import com.nexters.fullstack.databinding.FragmentHomeBinding
import com.nexters.fullstack.ui.activity.home.HomeScreenshotActivity
import com.nexters.fullstack.ui.activity.home.HomeSearchActivity
import com.nexters.fullstack.ui.activity.SettingActivity
import com.nexters.fullstack.ui.adapter.HomeMainParentAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.presentaion.viewmodel.HomeMainViewModel
import com.nexters.fullstack.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeMainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_home
    override val viewModel: HomeMainViewModel by viewModel()

    private val homeMainAdapter = HomeMainParentAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            setVariable(BR.vm, viewModel)
        }
        initView()
        initObserver()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchScreenShotData()
    }

    private fun initView(){
        binding.rvParent.run {
            adapter = homeMainAdapter
            addItemDecoration(SpaceBetweenRecyclerDecoration(vertical = VERTICAL_SPACING))
        }
    }

    private fun initObserver(){
        with(viewModel.state()){
            screenshotGroups.observe(viewLifecycleOwner, {
                homeMainAdapter.addItems(it)
            })
            recentImages.observe(viewLifecycleOwner, {
                viewModel.updateScreenshotGroup(recentGroup = it)
            })
            likedImages.observe(viewLifecycleOwner, {
                viewModel.updateScreenshotGroup(likedGroup = it)
            })
        }
    }

    private fun initListener(){
        homeMainAdapter.setItemClickListener { _, position, _ ->
            val intent : Intent
            when(position){
                0 -> intent = Intent(this.context, HomeSearchActivity::class.java)
                1 -> {
                    intent = Intent(this.context, HomeScreenshotActivity::class.java)
                    intent.putExtra(Constants.IMAGE_LIST, viewModel.state().recentImages.value)
                }
                2 ->{
                    intent = Intent(this.context, HomeScreenshotActivity::class.java)
                    intent.putExtra(Constants.IMAGE_LIST, viewModel.state().likedImages.value)
                }
                else -> throw Exception()
            }
            startActivity(intent)
        }

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    companion object {
        const val VERTICAL_SPACING = 10

        fun getInstance(): HomeFragment {
            val instance = HomeFragment().apply { bundleOf("tag" to "homeFragment") }
            return instance
        }
    }

}