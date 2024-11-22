package com.nexters.fullstack.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityCreateLabelBinding
import com.nexters.fullstack.data.db.entity.LabelModel
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.LabelingState
import com.nexters.fullstack.presentaion.model.MainMakeLabelSource
import com.nexters.fullstack.ui.widget.bottomsheet.mapper.mapToPallet
import com.nexters.fullstack.presentaion.viewmodel.LabelingViewModel
import com.nexters.fullstack.util.extension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateLabelActivity : BaseActivity<ActivityCreateLabelBinding, LabelingViewModel>() {
    override val layoutRes: Int = R.layout.activity_create_label

    override val viewModel: LabelingViewModel by viewModel()
    private val viaLabelData: LabelEntity? by lazy { intent.getParcelableExtra(Constants.LABEL_MODIFY_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
        initToolbar()
        setIntiView()
        bind {
            it.vm = viewModel
            it.data = viaLabelData
        }

        setOnClickListener()
        onObserve()
    }

    private fun onObserve() {
        with(viewModel.output) {
            finish().observe(this@CreateLabelActivity) {
                if (it != null) {
                    setResult(Activity.RESULT_OK)
                    this@CreateLabelActivity.finish()
                }
            }
            getCloseKeyboard().observe(this@CreateLabelActivity) {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.etLabelText.windowToken,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }
            getToastMessage().observe(this@CreateLabelActivity) {
                if(!it.isNullOrEmpty()) { showToast(it) }
            }
        }
    }

    private fun setOnClickListener() {
        binding.palletLayout.setOnLabelClickListener = {
            viewModel.input.clickLabel(it)
        }
        binding.tvLabeling.setOnClickListener {
            if (viaLabelData != null) {
                viewModel.input.clickSaveButton(viaLabelData!!.id)
            } else {
                viewModel.input.clickSaveButton()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
    }

    private fun setIntiView() {
        if (viaLabelData != null) {
            binding.etLabelText.setText(viaLabelData!!.text)

            //커서 이동
            binding.etLabelText.setSelection(viaLabelData!!.text.length)
            viewModel.onCreateView(
                MainMakeLabelSource(
                    viaLabelData!!.text,
                    viaLabelData!!.mapToPallet()
                )
            )
            viewModel.input.setDidLabelingState(true)
            viewModel.input.clickLabel(viaLabelData!!.mapToPallet())
            viewModel.onTextChanged(viaLabelData!!.text)
        }
        binding.palletLayout.setOnInitView(viaLabelData?.mapToPallet())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}