package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.SearchLabelUseCase
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.printSt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class HomeSearchViewModel constructor(
    private val searchLabelUseCase: SearchLabelUseCase
) : BaseViewModel() {
    private val state = State()

    private val selectedLabelList = mutableListOf<LabelEntity>()
    private val myLabelList = mutableListOf<LabelEntity>()
    private val recentlySearchList = mutableListOf<LabelEntity>()
    private val searchResultList = mutableListOf<LabelEntity>()

    private val resultFindLabels = MutableLiveData<List<LabelEntity>>()

    private val searchKeywordSubject = BehaviorSubject.create<String>()

    private val dispose = CompositeDisposable()

    val output = object : Output {
        override fun getSearchResultLabels(): LiveData<List<LabelEntity>> = resultFindLabels
    }

    init {
        // TODO : change for using cache data
        myLabelList.add(LabelEntity(1,  "Yellow", "test1"))
        myLabelList.add(LabelEntity(2,  "Red", "test1"))
        myLabelList.add(LabelEntity(3,  "Pink", "test1"))
        myLabelList.add(LabelEntity(4, "Purple Blue", "test1"))
        myLabelList.add(LabelEntity(5, "Green", "test1"))
        myLabelList.add(LabelEntity(6,  "Gray", "test1"))
        myLabelList.add(LabelEntity(7, "Orange", "test1"))

        recentlySearchList.add(LabelEntity(1,  "Yellow", "test1"))
        recentlySearchList.add(LabelEntity(2, "Red", "test1"))
        recentlySearchList.add(LabelEntity(3,  "Pink", "test1"))
        recentlySearchList.add(LabelEntity(4,  "Purple Blue", "test1"))
        recentlySearchList.add(LabelEntity(5, "Green", "test1"))

        state.myLabel.value = myLabelList
        state.recentlySearchLabel.value = recentlySearchList

        dispose.add(
            searchKeywordSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .flatMapSingle(searchLabelUseCase::buildUseCase)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { resultFindLabels.value = if (it.isEmpty()) emptyList() else it },
                    (::printSt)
                )
        )
    }

    fun state() = state

    fun setSearchMode() {
        state.mode.value = ViewMode.SEARCH
    }

    fun setRecommendMode() {
        state.mode.value = ViewMode.RECOMMEND
    }

    fun selectLabel(position: Int, type: ListType) {
        val selectedLabel: LabelEntity
        when (type) {
            ListType.MY_LABEL -> {
                selectedLabel = myLabelList.removeAt(position)
                state.myLabel.value = myLabelList
            }
            ListType.RECENT_SEARCH_LABEL -> {
                selectedLabel = recentlySearchList.removeAt(position)
                state.recentlySearchLabel.value = recentlySearchList
            }
        }
        selectedLabelList.add(selectedLabel)
        state.selectedLabel.value = selectedLabelList
    }

    data class State(
        val mode: MutableLiveData<ViewMode> = MutableLiveData(ViewMode.RECOMMEND),
        val selectedLabel: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val recentlySearchLabel: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val myLabel: MutableLiveData<List<LabelEntity>> = MutableLiveData(),
        val searchValue: MutableLiveData<String> = MutableLiveData(),
        val searchResult: MutableLiveData<List<LabelEntity>> = MutableLiveData()
    )

    enum class ViewMode {
        RECOMMEND, SEARCH
    }

    enum class ListType {
        MY_LABEL, RECENT_SEARCH_LABEL
    }

    interface Output {
        fun getSearchResultLabels(): LiveData<List<LabelEntity>>
    }

    fun onNext(value: String) {
        searchKeywordSubject.onNext(value)
    }
}