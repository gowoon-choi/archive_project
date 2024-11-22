package com.nexters.fullstack.ui.activity

import android.os.Bundle
import com.bumptech.glide.Glide
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityScreenshotDetailBinding
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.viewmodel.ScreenshotDetailViewModel
import com.nexters.fullstack.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScreenshotDetailActivity : BaseActivity<ActivityScreenshotDetailBinding, ScreenshotDetailViewModel>() {
    override val layoutRes: Int = R.layout.activity_screenshot_detail
    override val viewModel: ScreenshotDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initObserver()
    }

    private fun initData(){
        viewModel.state().image.value = intent.getSerializableExtra(Constants.IMAGE) as ImageEntity
    }

    private fun initObserver(){
        viewModel.state().image.observe(this, {
            Glide.with(this@ScreenshotDetailActivity)
                .load(it.image.originUrl)
                .into(binding.ivImage)
        })
    }
}