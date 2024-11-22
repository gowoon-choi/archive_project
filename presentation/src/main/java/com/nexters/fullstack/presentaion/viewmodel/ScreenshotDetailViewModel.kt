package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.model.Screenshot

class ScreenshotDetailViewModel : BaseViewModel() {
    private val state: State = State()

    fun state(): State = state

    data class State(
        val image : MutableLiveData<ImageEntity> = MutableLiveData(),
        val mode : MutableLiveData<Mode> = MutableLiveData(Mode.INFO)
    )

    enum class Mode{
        FULL, INFO
    }
}