package com.nexters.fullstack.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.helper.PermissionHelper
import com.nexters.fullstack.model.ActivityResultData
import io.reactivex.processors.PublishProcessor
import org.koin.android.ext.android.inject

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var binding: VB
        private set

    abstract val layoutRes: Int
    abstract val viewModel: VM

    internal val permissionHelper by inject<PermissionHelper>()

    internal val onActivityResultProcessor = PublishProcessor.create<ActivityResultData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        if (!permissionHelper.isAcceptPermission()) {
            ActivityCompat.requestPermissions(
                this,
                permissionHelper.requestPermissions.toTypedArray(),
                permissionHelper.requestCode
            )
        }
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
    }

    fun bind(body: (VB) -> Unit) {
        binding.run(body)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultProcessor.onNext(ActivityResultData(requestCode, resultCode, data))
    }
}