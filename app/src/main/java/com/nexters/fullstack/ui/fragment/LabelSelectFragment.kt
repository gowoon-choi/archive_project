package com.nexters.fullstack.ui.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.nexters.feature.util.ColorUtils
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.databinding.FragmentLabelSelectBinding
import com.nexters.fullstack.R
import com.nexters.fullstack.util.extension.toPx
import com.nexters.fullstack.model.ActivityResultData
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.presentaion.model.ViewState
import com.nexters.fullstack.ui.adapter.MyLabelAdapter
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.presentaion.viewmodel.LabelingViewModel
import com.nexters.fullstack.BR
import com.nexters.fullstack.presentaion.model.FileImageViewData
import com.nexters.fullstack.util.fadeInAnimation
import com.nexters.fullstack.util.fadeOutAnimation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LabelSelectFragment : BaseFragment<FragmentLabelSelectBinding, LabelingViewModel>() {
    override val layoutRes: Int = R.layout.fragment_label_select
    override val viewModel: LabelingViewModel by sharedViewModel()
    private val labelAdapter = MyLabelAdapter()

    init {
        labelAdapter.callback = {
            updateView(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            setVariable(BR.vm, viewModel)
        }

        setOnInitView()
        setInitOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.output.getLocalLabels().observe(this.viewLifecycleOwner) {
            labelAdapter.addItems(it)
            labelAdapter.notifyDataSetChanged()
        }
    }

    private fun setOnInitView() {
        binding.rvLabel.adapter = labelAdapter
        binding.rvLabel.addItemDecoration(SpaceBetweenRecyclerDecoration(vertical = 10))
    }

    private fun setInitOnClickListener() {
        with(viewModel.input) {
            binding.tvAddLabel.setOnClickListener {
                clickAppbar(ViewState.Add)
            }
            binding.saveButton.setOnClickListener {
                clickLabelingButton(
                    labelAdapter.selectedLabel,
                    arguments?.getParcelable(Constants.LABEL_BUNDLE_KEY) ?: FileImageViewData("", 0)
                )
            }
        }
    }

    private fun updateView(labels: MutableList<LabelViewData>) {
        val visibility = if (labels.isEmpty()) View.GONE else View.VISIBLE


        binding.selectLinearLayout.visibility = visibility
        binding.tvSelectedTitle.visibility = visibility

        binding.tvSelectedTitle.text = getString(R.string.label_select_fragment_title, labels.size)


        if (labels.isEmpty()) {
            binding.selectLinearLayout.fadeOutAnimation()
        } else {
            binding.selectLinearLayout.fadeInAnimation()

            binding.selectLinearLayout.removeAllViews()

            val spannableString = SpannableString(binding.tvSelectedTitle.text)

            val spanColor = ForegroundColorSpan(Color.parseColor("#66387CFF"))

            spannableString.setSpan(
                spanColor,
                binding.tvSelectedTitle.text.lastIndex,
                binding.tvSelectedTitle.text.lastIndex + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.tvSelectedTitle.text = spannableString

            for (i in labels.indices) {
                val layout = LayoutInflater.from(requireContext())
                    .inflate(R.layout.selected_label_item, null)
                val frameLayoutParams = FrameLayout.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                frameLayoutParams.setMargins(0, 0, 10.toPx, 0)
                layout.layoutParams = frameLayoutParams
                val frameLayout = layout.findViewById<FrameLayout>(R.id.bg_selected)
                val title = layout.findViewById<TextView>(R.id.tv_label)
                val cancelButton = layout.findViewById<ImageView>(R.id.cancel_button)
                cancelButton.setOnClickListener {
                    labels.remove(labels[i])
                    binding.selectLinearLayout.removeViewAt(i)
                    updateView(labels)
                    labelAdapter.notifyDataSetChanged()
                }
                title.text = labels[i].name
                setBackgroundTint(cancelButton, title, frameLayout, color = labels[i].color)
                binding.selectLinearLayout.addView(layout, 0)
            }

        }
    }

    private fun setBackgroundTint(
        imageView: ImageView,
        textView: TextView,
        frameLayout: FrameLayout,
        color: String
    ) {
        val colorUtils = ColorUtils(color, requireContext()).getActive()
        imageView.setColorFilter(colorUtils)
        textView.setTextColor(colorUtils)
        frameLayout.background.setTint(colorUtils)
    }

    override fun onDestroyView() {
        labelAdapter.selectedLabelClear()
        super.onDestroyView()
    }

    companion object {

        fun getInstance(localFileData: FileImageViewData? = null): LabelSelectFragment {
            val fragment = LabelSelectFragment()
            val bundle = Bundle().apply {
                putParcelable(Constants.LABEL_BUNDLE_KEY, localFileData)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityResult(activityResultData: ActivityResultData) {
        if (activityResultData.resultCode == Activity.RESULT_OK) {
            labelAdapter.notifyDataSetChanged()
        } else if (activityResultData.result != null && activityResultData.result is LabelViewData) {
            val labelSource = activityResultData.result
            labelAdapter.addSelectedItem(labelSource)
            labelAdapter.notifyDataSetChanged()
        }
    }
}