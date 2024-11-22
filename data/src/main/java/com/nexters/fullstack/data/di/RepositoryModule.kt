package com.nexters.fullstack.data.di

import com.nexters.fullstack.data.repository.LabelRepositoryImpl
import com.nexters.fullstack.data.repository.AlbumRepositoryImpl
import com.nexters.fullstack.data.repository.ImageRepositoryImpl
import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.repository.LabelRepository
import org.koin.dsl.module

val albumListModule = module {
    factory<AlbumRepository> { AlbumRepositoryImpl(get()) }

    factory<LabelRepository> { LabelRepositoryImpl(get()) }

    factory<ImageRepository> { ImageRepositoryImpl(get()) }
}