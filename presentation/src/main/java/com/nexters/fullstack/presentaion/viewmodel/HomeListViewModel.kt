package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.model.HomeList
import com.nexters.fullstack.presentaion.model.HomeListType

class HomeListViewModel : BaseViewModel() {
    private val state: State = State()

    fun state(): State = state

    fun initImageData(data: HomeList){
        state.images.value = data.images
        if(data.type == HomeListType.RECENT) state.viewMode.value = ViewMode.RECENT
        else state.viewMode.value = ViewMode.LIKE
    }

    fun changeMode(){
        when(state.selectMode.value){
            SelectMode.DEFAULT -> state.selectMode.value = SelectMode.SELECTION
            SelectMode.SELECTION -> state.selectMode.value = SelectMode.DEFAULT
        }
    }

    fun clearSelectedImages(){
        state.selectedImages.value = mutableListOf()
    }

    fun selectImage(selectedImages: List<ImageEntity>) {
        state.selectedImages.value = selectedImages
    }

    data class State(
        val images : MutableLiveData<List<ImageEntity>> = MutableLiveData(),
        val selectedImages : MutableLiveData<List<ImageEntity>> = MutableLiveData(),
        val viewMode : MutableLiveData<ViewMode> = MutableLiveData(),
        val selectMode : MutableLiveData<SelectMode> = MutableLiveData(SelectMode.DEFAULT)
    )

    enum class SelectMode{
        DEFAULT, SELECTION
    }

    enum class ViewMode{
        RECENT, LIKE
    }
}