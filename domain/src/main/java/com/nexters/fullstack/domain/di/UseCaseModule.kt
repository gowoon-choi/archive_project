package com.nexters.fullstack.domain.di

import com.nexters.fullstack.domain.usecase.*
import com.nexters.fullstack.domain.usecase.GetUnlabeledImages
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetUnlabeledImages(
            get(),
            get()
        )
    }
    single { UpdateLabel(get()) }
    single { GetLabels(get()) }
    single { RequestLabeling(get()) }
    single { RequestUnLabeling(get()) }
    single { RejctLabeling(get()) }
    single {
        LoadImageUseCase(
            get()
        )
    }
    single { SearchImagesByLabel(get()) }
    single { SearchImagesBySingleLabel(get()) }
    single { PostBookmarkingImageUseCase(get()) }
    single { DeleteImageUseCase(get()) }
    single { GetDetailImage(get(), get()) }
    single { SearchLabelUseCase(get()) }
    single { GetLabelHomeData(get()) }
    single { DeleteLabels(get()) }
    single { GetSearchHomeData(get()) }
    single { GetRecentlyImageData(get(), get()) }
    single { GetHomeData(get(), get()) }
    single { DeleteImages(get()) }
    single { CountingViewCountUseCase(get()) }
    single { DeleteFileUseCase(get()) }
}