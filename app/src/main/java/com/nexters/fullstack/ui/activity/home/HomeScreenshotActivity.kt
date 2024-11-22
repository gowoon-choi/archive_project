package com.nexters.fullstack.ui.activity.home

import android.content.Intent
import android.os.Bundle
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityHomeScreenshotBinding
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.model.HomeList
import com.nexters.fullstack.presentaion.viewmodel.HomeListViewModel
import com.nexters.fullstack.ui.activity.ScreenshotDetailActivity
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenshotActivity : BaseActivity<ActivityHomeScreenshotBinding, HomeListViewModel>() {
    override val layoutRes: Int = R.layout.activity_home_screenshot
    override val viewModel: HomeListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            it.vm = viewModel
        }
        initData()
        initView()
        initListener()
    }

    private fun initData(){
        intent.getParcelableExtra<HomeList>(Constants.IMAGE_LIST)?.let { viewModel.initImageData(it) }
    }

    private fun initView(){
        val images: List<ImageEntity> = viewModel.state().images.value ?: emptyList()
        binding.rvImages.setImages(images as ArrayList<ImageEntity>)

        val spaceDecoration = SpaceBetweenRecyclerDecoration(vertical = RV_MARGIN)
        binding.rvImages.addItemDecoration(spaceDecoration)
    }

    private fun initListener(){
        with(binding){
            ivBack.setOnClickListener {
                onBackPressed()
            }
            tvSelection.setOnClickListener {
                rvImages.picker()
                viewModel.clearSelectedImages()
                viewModel.changeMode()
            }
            ivCancel.setOnClickListener {
                rvImages.picker()
                viewModel.clearSelectedImages()
                viewModel.changeMode()
            }
            rvImages.getImageAdapter().setItemClickListener { _, i, image ->
                when(viewModel.state().selectMode.value){
                    HomeListViewModel.SelectMode.DEFAULT ->{
                        val intent = Intent(this@HomeScreenshotActivity, ScreenshotDetailActivity::class.java)
                        intent.putExtra(Constants.IMAGE, image)
                        startActivity(intent)
                    }
                    else -> {
                        rvImages.selectImage(i)
                        viewModel.selectImage(rvImages.getSelectedImages())
                    }
                }
            }
        }
    }

    companion object{
        const val RV_MARGIN = 5
    }
}