package com.nexters.fullstack.presentaion.model

sealed class ViewState {
    object Selected : ViewState()

    object Search : ViewState()

    object Add : ViewState()
}
