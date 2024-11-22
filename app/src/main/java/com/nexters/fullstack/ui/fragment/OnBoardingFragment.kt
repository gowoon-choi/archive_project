package com.nexters.fullstack.ui.fragment

import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.databinding.FragmentOnboardingBinding
import com.nexters.fullstack.presentaion.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding, OnBoardingViewModel>(), CoroutineScope {
    override val layoutRes: Int = R.layout.fragment_onboarding
    override val viewModel: OnBoardingViewModel by viewModel()
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
    private lateinit var data : OnBoardingViewModel.OnBoardingData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mJob = Job()
        initAnimation()
    }

    override fun onResume() {
        super.onResume()
        playAnimation()
    }

    override fun onPause() {
        super.onPause()
        resumeAnimation()
    }

    private fun initAnimation(){
        data = viewModel.getItem(requireArguments().getInt(BUNDLE_KEY))
        binding.lottie.setAnimation(LOTTIE_PREFIX + data.order + LOTTIE_SUFFIX)
    }

    private fun playAnimation(){
        launch {
            val job = async {
                if(data.order == DELAY_ORDER) {
                    delay(DELAY_TIME)
                }
            }
            job.await()
            binding.lottie.playAnimation()
            binding.lottie.repeatCount = LottieDrawable.INFINITE
        }
    }

    private fun resumeAnimation(){
        binding.lottie.cancelAnimation()
        binding.lottie.frame = 0
    }

    companion object {
        private const val LOTTIE_PREFIX = "onboarding"
        private const val LOTTIE_SUFFIX = ".json"
        private const val BUNDLE_KEY = "order"
        private const val DELAY_ORDER = 2
        private const val DELAY_TIME = 300L
        fun newInstance(order: Int): OnBoardingFragment {
            val fragment = OnBoardingFragment()
            val bundle = Bundle()
            bundle.putInt(BUNDLE_KEY, order)
            fragment.arguments = bundle
            return fragment
        }
    }
}