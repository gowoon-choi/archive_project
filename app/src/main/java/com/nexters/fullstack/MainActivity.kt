package com.nexters.fullstack

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.nexters.fullstack.util.Constants.BOTTOM_SHEET_ITEM
import com.nexters.fullstack.util.Constants.BOTTOM_SHEET_KEY
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityMainBinding
import com.nexters.fullstack.ui.fragment.LabelManagerFragment
import com.nexters.fullstack.ui.fragment.MyAlbumFragment
import com.nexters.fullstack.ui.fragment.home.HomeFragment
import com.nexters.fullstack.presentaion.viewmodel.MainViewModel
import com.nexters.fullstack.util.extension.replace
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    private val labelManagerFragment = LabelManagerFragment.getInstance()
    private val homeFragment = HomeFragment.getInstance()
    private val myAlbumFragment = MyAlbumFragment.getInstance()

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        bind { }
    }

    private fun initView() {
        supportFragmentManager.replace(
            binding.frameLayout.id,
            labelManagerFragment,
        )

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            fetchBottomSheet(itemId = item.itemId)
            true
        }
    }

    private fun fetchBottomSheet(itemId: Int) {
        firebaseAnalytics.logEvent(BOTTOM_SHEET_KEY) {
            param(FirebaseAnalytics.Param.ITEM_ID, bundleOf(BOTTOM_SHEET_ITEM to itemId))
        }
        when (itemId) {
            R.id.label -> changeFragment(labelManagerFragment)
            R.id.album -> changeFragment(myAlbumFragment)
            R.id.search -> changeFragment(homeFragment)
        }
    }

    private fun changeFragment(newFragment: Fragment) {
        supportFragmentManager.replace(binding.frameLayout.id, newFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionHelper.requestCode) {
            for (permission in permissions) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "허가가 필요합니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}