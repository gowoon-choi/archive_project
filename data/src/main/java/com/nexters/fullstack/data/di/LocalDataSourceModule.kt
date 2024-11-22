package com.nexters.fullstack.data.di

import com.nexters.fullstack.domain.local.LabelaryLocalDataSource
import com.nexters.fullstack.data.local.LabelaryLocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    single<LabelaryLocalDataSource.Label> { LabelaryLocalDataSourceImpl(get(), get(), get()) }

    single<LabelaryLocalDataSource.Image> { LabelaryLocalDataSourceImpl(get(), get(), get()) }
}