package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.model.bottomsheet.BottomSheetItem
import com.nexters.fullstack.presentaion.model.dialog.DeleteDialogItem
import com.nexters.fullstack.util.SingleLiveData

class BottomSheetViewModel : BaseViewModel() {
    private val _items = MutableLiveData<List<BottomSheetItem>>()
    val items: LiveData<List<BottomSheetItem>>
        get() = _items

    private val _deleteDialog = SingleLiveData<DeleteDialogItem>()

    init {
        _items.value = listOf(
            BottomSheetItem(type = UPDATE, "라벨 수정하기"),
            BottomSheetItem(type = DELETE, "라벨 삭제하기")
        )
    }

    val input = object : Input {
        override fun setDialogItem(item: DeleteDialogItem) {
            _deleteDialog.value = item
        }
    }

    val output = object : Output {
        override fun getDialogItem(): LiveData<DeleteDialogItem> = _deleteDialog
    }

    companion object {
        private const val UPDATE = 7000

        private const val DELETE = 7001
    }

    interface Input {
        fun setDialogItem(item: DeleteDialogItem)
    }

    interface Output {
        fun getDialogItem(): LiveData<DeleteDialogItem>
    }
}