package com.nexters.fullstack.data.di

import com.nexters.fullstack.data.local.filesystem.FileSystemImages
import com.nexters.fullstack.data.local.filesystem.FileSystemImagesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localImageModule = module {
    factory<FileSystemImages> {
        FileSystemImagesImpl(androidContext())
    }
}