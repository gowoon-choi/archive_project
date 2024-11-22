package com.nexters.fullstack.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.nexters.fullstack.MainActivity
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivitySplashBinding
import com.nexters.fullstack.util.PrefDataStoreManager
import com.nexters.fullstack.presentaion.viewmodel.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_splash
    override val viewModel: MainViewModel by viewModel()

    private val prefDataStoreManager = PrefDataStoreManager()
//    <  using shared Preference  >
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        <  using shared Preference  >
//        sharedPref = this.getPreferences(MODE_PRIVATE)
//        sharedPref.getBoolean(Constants.FIRST_LOADING, true)

        var intent = Intent(this, MainActivity::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            GlobalScope.launch {
                prefDataStoreManager.readIsFirstFlow().collect {
                    if (it) intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                }
            }
            delay(SPLASH_VIEW_TIME)
            startActivity(intent)
        }
    }

    companion object{
        private const val SPLASH_VIEW_TIME = 1000L
    }
}