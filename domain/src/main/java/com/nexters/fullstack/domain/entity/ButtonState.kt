package com.nexters.fullstack.domain.entity

sealed class LabelSwipeState {
    object Approve : LabelSwipeState()
    object Reject : LabelSwipeState()
}