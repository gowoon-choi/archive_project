package com.nexters.fullstack.ui.widget

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nexters.fullstack.R
import com.nexters.fullstack.databinding.DialogCreateLabelBinding

class CreateAlbumBottomSheet(context: Context) :
    BottomSheetDialog(context, R.style.BottomSheetDefaultStyle) {

    lateinit var binding: DialogCreateLabelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCreateLabelBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initView()
    }

    override fun onStart() {
        super.onStart()

        if (behavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initView() {
        binding.close.setOnClickListener { dismiss() }
    }
}