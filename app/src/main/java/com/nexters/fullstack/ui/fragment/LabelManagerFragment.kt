package com.nexters.fullstack.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.os.bundleOf
import com.nexters.fullstack.util.Constants.LABEL_BUNDLE_KEY
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.databinding.FragmentLabelManagerBinding
import com.nexters.fullstack.presentaion.viewmodel.MainViewModel
import com.nexters.fullstack.ui.activity.LabelingActivity
import com.nexters.fullstack.ui.adapter.MainStackAdapter
import com.yuyakaido.android.cardstackview.*
import com.nexters.fullstack.BR
import com.nexters.fullstack.domain.entity.LabelSwipeState
import com.nexters.fullstack.presentaion.model.FileImageViewData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LabelManagerFragment : BaseFragment<FragmentLabelManagerBinding, MainViewModel>(),
    CardStackListener {
    override val layoutRes: Int = R.layout.fragment_label_manager
    override val viewModel: MainViewModel by sharedViewModel()

    private val stackAdapter: MainStackAdapter by lazy { MainStackAdapter() }

    private val manager: CardStackLayoutManager by lazy {
        CardStackLayoutManager(
            requireContext(),
            this
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            setVariable(BR.vm, viewModel)
        }

        onViewInit()
        setObserver()
    }

    private fun onViewInit() {
        with(manager) {
            setStackFrom(StackFrom.None)
            setStackFrom(StackFrom.Right)
            setVisibleCount(4)
            setTranslationInterval(11.0f)
            setScaleInterval(0.9f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = stackAdapter
    }


    private fun setObserver() {
        with(viewModel.output) {
            startLabelling().observe(this@LabelManagerFragment.viewLifecycleOwner, {
                if (it != null) {
                    startActivityWithData(it)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.input.refreshImages()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        //no-op

        binding.skipButton.visibility = View.GONE
        binding.labeledButton.visibility = View.GONE

    }

    override fun onCardSwiped(direction: Direction?) {
        // left -> reject
        // right -> approve
        if (stackAdapter.isSwipe.not())
            return

        when (direction) {
            Direction.Right -> viewModel.input.swipe(LabelSwipeState.Approve)
            Direction.Left -> viewModel.input.swipe(LabelSwipeState.Reject)
        }
    }

    override fun onCardCanceled() {
        binding.skipButton.visibility = View.VISIBLE
        binding.labeledButton.visibility = View.VISIBLE
    }

    override fun onCardAppeared(view: View?, position: Int) {
        binding.skipButton.visibility = View.VISIBLE
        binding.labeledButton.visibility = View.VISIBLE
        viewModel.input.currentImage(stackAdapter.getItem(position))
    }


    override fun onCardRewound() = Unit

    override fun onCardDisappeared(view: View?, position: Int) = Unit

    override fun onDestroyView() {
        binding.cardStackView.layoutManager = CardStackLayoutManager(requireContext(), this)
        super.onDestroyView()
    }

    private fun startActivityWithData(item: FileImageViewData) {
        val intent = Intent(this@LabelManagerFragment.context, LabelingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        intent.putExtras(bundleOf(LABEL_BUNDLE_KEY to item))
        startActivityForResult(intent, 2000)
    }

    companion object {
        fun getInstance(): LabelManagerFragment {
            val fragment =
                LabelManagerFragment().apply { bundleOf("tag" to "LabelManagerFragment") }
            return fragment
        }
    }
}