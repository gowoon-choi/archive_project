package com.nexters.fullstack

import android.app.Application
import com.nexters.fullstack.data.di.albumListModule
import com.nexters.fullstack.data.di.databaseModule
import com.nexters.fullstack.data.di.localDataSourceModule
import com.nexters.fullstack.data.di.localImageModule
import com.nexters.fullstack.di.permissionModule
import com.nexters.fullstack.domain.di.*
import com.nexters.fullstack.presentaion.di.presentationMapper
import com.nexters.fullstack.presentaion.di.viewModelModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    companion object{
        lateinit var app: App
    }
    override fun onCreate() {
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    useCaseModule,
                    presentationMapper,
                    viewModelModule,
                    permissionModule,
                    albumListModule,
                    localImageModule,
                    databaseModule,
                    localDataSourceModule
                )
            )
        }

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
        super.onCreate()
        app = this
    }
}