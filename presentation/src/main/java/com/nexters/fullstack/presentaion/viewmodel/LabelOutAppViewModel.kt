package com.nexters.fullstack.presentaion.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.*
import com.nexters.fullstack.presentaion.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LabelOutAppViewModel(
    private val getSearchHomeData: GetSearchHomeData,
    private val searchLabelUseCase: SearchLabelUseCase
) : BaseViewModel() {
    private val state: State = State()
    private val myLabelList = mutableListOf<LabelEntity>()
    private val selectedLabelList = mutableListOf<LabelEntity>()
    private val recentlySearchList = mutableListOf<LabelEntity>()

    private val disposable = CompositeDisposable()

    fun state(): State = state

    fun fetchScreenshot(uri: Uri) {
        state.imageUri.value = uri
    }

    fun fetchHomeData(){
        disposable.add(
            getSearchHomeData.buildUseCase(Unit).cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    myLabelList.clear()
                    myLabelList.addAll(data.labels)
                    recentlySearchList.clear()
                    recentlySearchList.addAll(data.recentlyLabels)
                    if(myLabelList.isNullOrEmpty()) setViewState(ViewState.NO_LABEL)
                    else{
                        state.myLabels.value = myLabelList
                        setViewState(ViewState.MY_LABEL)
                    }
                    state.recentlySearch.value = recentlySearchList
                }, {it.printStackTrace()})
        )
    }

    fun selectLabel(name: String) {
        for (label in myLabelList) {
            if (label.text == name) {
                myLabelList.remove(label)
                selectedLabelList.add(0, label)
                break
            }
        }
        state.myLabels.value = myLabelList
        state.selectedLabels.value = selectedLabelList
        state.viewState.value = ViewState.MY_LABEL
    }

    fun selectLabel(position: Int) {
        val selectedLabel = myLabelList[position]
        myLabelList.removeAt(position)
        selectedLabelList.add(0, selectedLabel)
        state.myLabels.value = myLabelList
        state.selectedLabels.value = selectedLabelList
    }

    fun deselectLabel(position: Int) {
        val selectedLabel = selectedLabelList[position]
        selectedLabelList.removeAt(position)
        myLabelList.add(selectedLabel)
        state.myLabels.value = myLabelList
        state.selectedLabels.value = selectedLabelList
    }

    fun completeLabeling() {
        // TODO : 공유로 받아온 이미지 URI에서 absolute path 이용해서 GetDetailImage then RequestLabelling ?
    }

    fun setViewState(viewState: ViewState) {
        state.viewState.value = viewState
    }

    fun clearSearchKeyword() {
        state.searchKeyword.value = ""
    }

    fun search() {
        disposable.add(
            searchLabelUseCase.buildUseCase(state.searchKeyword.value ?: "").cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ searchResult ->
                    state.searchResult.value = searchResult
                }, { it.printStackTrace() })
        )
    }

    init {
        fetchHomeData()
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    data class State(
        val imageUri: MutableLiveData<Uri> = MutableLiveData(),
        val myLabels: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val selectedLabels: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val searchKeyword: MutableLiveData<String> = MutableLiveData(),
        val searchResult: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val recentlySearch: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val viewState: MutableLiveData<ViewState> = MutableLiveData()
    )

    enum class ViewState {
        NO_LABEL,
        MY_LABEL,
        RECENT_LABEL,
        SEARCH_RESULT,
        NO_RESULT
    }
}