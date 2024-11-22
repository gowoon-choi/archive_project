package com.nexters.fullstack.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.util.NotFoundViewState
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivityLabelOutappBinding
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.util.extension.hideKeyboard
import com.nexters.fullstack.ui.adapter.OutAppLabelAdapter
import com.nexters.fullstack.ui.adapter.SelectedLabelAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.presentaion.viewmodel.LabelOutAppViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LabelOutAppActivity : BaseActivity<ActivityLabelOutappBinding, LabelOutAppViewModel>() {
    override val layoutRes: Int = R.layout.activity_label_outapp
    override val viewModel: LabelOutAppViewModel by viewModel()

    private val myLabelAdapter : OutAppLabelAdapter = OutAppLabelAdapter(LabelOutAppViewModel.ViewState.MY_LABEL)
    private val selectedLabelAdapter : SelectedLabelAdapter = SelectedLabelAdapter()
    private val recentlyLabelAdapter : OutAppLabelAdapter = OutAppLabelAdapter(LabelOutAppViewModel.ViewState.RECENT_LABEL)
    private val searchResultAdapter : OutAppLabelAdapter = OutAppLabelAdapter(LabelOutAppViewModel.ViewState.SEARCH_RESULT)
    private val addLabelAdapter : OutAppLabelAdapter = OutAppLabelAdapter(LabelOutAppViewModel.ViewState.NO_RESULT)
    private val noLabelAdapter : OutAppLabelAdapter = OutAppLabelAdapter(LabelOutAppViewModel.ViewState.NO_LABEL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            it.vm = viewModel
        }
        initData()
        initView()
        initListener()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchHomeData()
    }

    private fun initData(){
        when{
            intent?.action == Intent.ACTION_SEND
                    && intent.type?.startsWith(Constants.IMAGE_PREFIX) == true -> {
                (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let { uri: Uri ->
                    viewModel.fetchScreenshot(uri)
                }
            }
        }
    }

    private fun initView() {
        with(viewModel.state()) {
            imageUri.observe(this@LabelOutAppActivity, {
                Glide.with(this@LabelOutAppActivity)
                    .load(it)
                    .into(binding.ivScreenshot)
            })
        }

        with(binding){
            ivScreenshot.clipToOutline = true

            val spaceDecoration = SpaceBetweenRecyclerDecoration(RV_SPACING_DP, RV_SPACING_DP)
            rvSelectedLabel.adapter = selectedLabelAdapter
            rvSelectedLabel.addItemDecoration(spaceDecoration)
            rvLabel.addItemDecoration(spaceDecoration)
        }
    }

    private fun initListener(){
        with(binding){
            ivCancel.setOnClickListener {
                onBackPressed()
            }
            ivBack.setOnClickListener {
                viewModel.setViewState(LabelOutAppViewModel.ViewState.MY_LABEL)
            }
            ivClear.setOnClickListener {
                viewModel.clearSearchKeyword()
            }
            tvDone.setOnClickListener {
                viewModel.completeLabeling()
                finish()
            }
            etSearch.setOnFocusChangeListener { _, isFocused ->
                if(isFocused){
                    viewModel.setViewState(LabelOutAppViewModel.ViewState.RECENT_LABEL)
                    containerAppbar.setExpanded(false)
                }
                else{
                    etSearch.hideKeyboard()
                }
            }
            etSearch.setOnEditorActionListener { _, action, _ ->
                var handled = false
                if(action == EditorInfo.IME_ACTION_DONE){
                    etSearch.clearFocus()
                    handled = true
                }
                handled
            }
        }
        myLabelAdapter.setItemClickListener { _, i, _ ->
            viewModel.selectLabel(i)
        }
        recentlyLabelAdapter.setItemClickListener { _, _, labelSource ->
            labelSource?.let {
                viewModel.selectLabel(it.text)
            }
        }
        searchResultAdapter.setItemClickListener { _, _, labelSource ->
            labelSource?.let {
                viewModel.selectLabel(it.text)
            }
        }
        selectedLabelAdapter.setItemClickListener { _, i, _ ->
            viewModel.deselectLabel(i)
        }
        addLabelAdapter.setItemClickListener { _, _, labelSource ->
            val intent = Intent(this, CreateLabelActivity::class.java)
            intent.putExtra(Constants.LABEL_TITLE, labelSource?.text)
            startActivity(intent)
        }
        noLabelAdapter.setItemClickListener { _, _, _ ->
            startActivity(Intent(this, CreateLabelActivity::class.java))
        }
    }

    private fun initObserver(){
        with(viewModel.state()){
            recentlySearch.observe(this@LabelOutAppActivity, {
                recentlyLabelAdapter.calDiff(it as MutableList<LabelEntity>)
            })
            myLabels.observe(this@LabelOutAppActivity, {
                myLabelAdapter.addItems(it)
            })
            selectedLabels.observe(this@LabelOutAppActivity, {
                selectedLabelAdapter.addItems(it)
                binding.rvSelectedLabel.scrollToPosition(0)
            })
            searchResult.observe(this@LabelOutAppActivity, {
                searchResultAdapter.calDiff(it as MutableList<LabelEntity>)
                if(it.isNullOrEmpty()){
                    viewModel.setViewState(LabelOutAppViewModel.ViewState.NO_RESULT)
                    addLabelAdapter.clearItems()
                    addLabelAdapter.addItems(listOf(LabelEntity(-1, "ignored", viewModel.state().searchKeyword.value ?: "")))
                }else{
                    viewModel.setViewState(LabelOutAppViewModel.ViewState.SEARCH_RESULT)
                }
            })
            searchKeyword.observe(this@LabelOutAppActivity, {
                if(it.isNullOrBlank()){
                    if(viewModel.state().viewState.value != LabelOutAppViewModel.ViewState.MY_LABEL){
                        viewModel.setViewState(LabelOutAppViewModel.ViewState.RECENT_LABEL)
                    }
                }
                else{
                    viewModel.search()
                }
            })
            viewState.observe(this@LabelOutAppActivity, {
                when(it){
                    LabelOutAppViewModel.ViewState.MY_LABEL -> {
                        binding.rvLabel.layoutManager = FlexboxLayoutManager(this@LabelOutAppActivity).apply {
                                flexWrap = FlexWrap.WRAP
                                flexDirection = FlexDirection.ROW
                            }
                        binding.etSearch.clearFocus()
                        binding.containerAppbar.setExpanded(true)
                        binding.rvLabel.adapter = myLabelAdapter
                        viewModel.clearSearchKeyword()
                    }
                    LabelOutAppViewModel.ViewState.RECENT_LABEL -> {
                        binding.rvLabel.adapter = recentlyLabelAdapter
                    }
                    LabelOutAppViewModel.ViewState.SEARCH_RESULT -> {
                        binding.rvLabel.adapter = searchResultAdapter
                    }
                    LabelOutAppViewModel.ViewState.NO_RESULT -> {
                        binding.rvLabel.adapter = addLabelAdapter
                    }
                    LabelOutAppViewModel.ViewState.NO_LABEL -> {
                        binding.rvLabel.layoutManager = GridLayoutManager(this@LabelOutAppActivity, 1)
                        binding.rvLabel.adapter = noLabelAdapter
                    }
                    else -> throw NotFoundViewState
                }
            })
        }
    }

    companion object{
        const val RV_SPACING_DP = 2
    }
}
