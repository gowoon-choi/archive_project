package com.nexters.fullstack.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.databinding.ActivitySearchLabelBinding
import com.nexters.fullstack.model.ActivityResultData
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.ui.adapter.MyLabelAdapter
import com.nexters.fullstack.presentaion.viewmodel.LabelingViewModel
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchLabelActivity : BaseActivity<ActivitySearchLabelBinding, LabelingViewModel>() {
    override val layoutRes: Int = R.layout.activity_search_label

    override val viewModel: LabelingViewModel by viewModel()

    private val labelAdapter = MyLabelAdapter(true)

    private val searchAdapter = MyLabelAdapter(true)

    init {
        searchAdapter.finish = { labelList ->
            finish()
            val intent = Intent(this@SearchLabelActivity, LabelingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            onActivityResultProcessor.onNext(ActivityResultData(result = labelList))
        }

        labelAdapter.finish = { labelList ->
            finish()
            val intent = Intent(this@SearchLabelActivity, LabelingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            onActivityResultProcessor.onNext(ActivityResultData(result = labelList))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            it.vm = viewModel
        }

        onViewInit()
        onObserve()
    }

    private fun onViewInit() {
        with(binding) {
            rvMyLabel.run {
                adapter = labelAdapter
                layoutManager = FlexboxLayoutManager(this@SearchLabelActivity).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                }
                addItemDecoration(
                    SpaceBetweenRecyclerDecoration(
                        VERTICAL_SPACING,
                        HORIZONTAL_SPACING
                    )
                )
            }
            rvResult.run {
                adapter = searchAdapter
                layoutManager = FlexboxLayoutManager(this@SearchLabelActivity).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                }
                addItemDecoration(
                    SpaceBetweenRecyclerDecoration(
                        VERTICAL_SPACING,
                        HORIZONTAL_SPACING
                    )
                )
            }
            rvRecentlySearch.run {
                adapter = MyLabelAdapter(true).apply {
                    addItems(
                        listOf(
                            LabelViewData(-1L,name = "강아지", color = "Yellow"),
                            LabelViewData(-1L,name = "충무로", color = "Red"),
                            LabelViewData(-1L,name = "홍대입구", color = "Purple Blue"),
                            LabelViewData(-1L,name = "넥스터즈", color = "Green"),
                            LabelViewData(-1L,name = "안드로이드", color = "Pink"),
                            LabelViewData(-1L,name = "개발", color = "Orange"),
                            LabelViewData(-1L,name = "관악구", color = "Violet")
                        )
                    )
                    finish = {}
                }
                layoutManager = FlexboxLayoutManager(this@SearchLabelActivity).apply {
                    flexWrap = FlexWrap.WRAP
                    flexDirection = FlexDirection.ROW
                }
                addItemDecoration(
                    SpaceBetweenRecyclerDecoration(
                        VERTICAL_SPACING,
                        HORIZONTAL_SPACING
                    )
                )
            }
        }
    }

    private fun onObserve() {
        with(viewModel.output) {
            finish().observe(this@SearchLabelActivity) { value ->
                if (value != null) {
                    this@SearchLabelActivity.finish()
                }
            }
            getLabelQuery().observe(this@SearchLabelActivity) { query ->
                val filterItems = getLocalLabels().value?.filter {
                    it.name.startsWith(query)
                }
                searchAdapter.addItems(filterItems ?: listOf())
                binding.tvMyLabelResultCount.text = searchAdapter.itemCount.toString()
                searchAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val HORIZONTAL_SPACING = 5

        private const val VERTICAL_SPACING = 10
    }
}