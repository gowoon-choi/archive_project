package com.nexters.fullstack.presentaion.di

import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.presentaion.model.FileImageViewData
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.presentaion.mapper.FileImageViewDataToEntityMapper
import com.nexters.fullstack.presentaion.mapper.LocalToPresentMapper
import com.nexters.fullstack.presentaion.viewmodel.AlbumViewModel
import com.nexters.fullstack.presentaion.viewmodel.LabelOutAppViewModel
import com.nexters.fullstack.presentaion.viewmodel.LabelingViewModel
import com.nexters.fullstack.presentaion.viewmodel.MainViewModel
import com.nexters.fullstack.presentaion.viewmodel.*
import com.nexters.fullstack.presentaion.viewmodel.detail.DetailAlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationMapper = module {
    single<Mapper<FileImageEntity, FileImageViewData>> { LocalToPresentMapper }
    single { FileImageViewDataToEntityMapper }
}

val viewModelModule = module {
    viewModel { LabelOutAppViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { LabelingViewModel(get(), get(), get(), get()) }
    viewModel { BottomSheetViewModel() }
    viewModel { AlbumViewModel(get(), get()) }
    viewModel { HomeMainViewModel(get(), get(), get()) }
    viewModel { HomeListViewModel() }
    viewModel { HomeSearchViewModel(get()) }
    viewModel { ScreenshotDetailViewModel() }
    viewModel { HomeSearchResultViewModel() }
    viewModel { DetailAlbumViewModel(get(), get(), get(), get(), get()) }
    viewModel { MyAlbumViewModel(get(), get()) }
}
