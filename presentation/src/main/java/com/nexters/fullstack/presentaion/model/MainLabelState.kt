package com.nexters.fullstack.presentaion.model


sealed class MainLabelState {
    object Approve : MainLabelState()

    object Reject : MainLabelState()
}
