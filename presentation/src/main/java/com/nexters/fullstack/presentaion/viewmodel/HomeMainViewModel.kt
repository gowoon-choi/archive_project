package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.usecase.DeleteImages
import com.nexters.fullstack.domain.usecase.GetHomeData
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.mapper.FileImageViewDataToEntityMapper
import com.nexters.fullstack.presentaion.model.HomeList
import com.nexters.fullstack.presentaion.model.HomeListType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeMainViewModel(
    private val getHomeData: GetHomeData,
    private val deleteImages: DeleteImages,
    private val mapper: FileImageViewDataToEntityMapper
) : BaseViewModel() {
    private lateinit var recentScreenshotList : HomeList
    private lateinit var likedScreenshotList : HomeList

    private val state: State = State()

    private val disposable = CompositeDisposable()

    fun state(): State = state

    fun fetchScreenShotData(){
        disposable.add(
            getHomeData.buildUseCase(Unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    recentScreenshotList = HomeList(HomeListType.RECENT, images = data.recentlyImages.map { mapper.toData(it) })
                    state.recentImages.value = recentScreenshotList
                    likedScreenshotList = HomeList(HomeListType.FAVORITE,images = data.bookmarkingImages)
                    state.likedImages.value = likedScreenshotList
                }, {it.printStackTrace()})
        )
    }

    fun updateScreenshotGroup(recentGroup: HomeList = recentScreenshotList, likedGroup: HomeList = likedScreenshotList){
        val screenshotGroupList = mutableListOf(recentGroup, likedGroup)
        state.screenshotGroups.value = screenshotGroupList
    }

    init {
        fetchScreenShotData()
    }

    data class State(
        val screenshotGroups: MutableLiveData<List<HomeList>> = MutableLiveData(),
        val recentImages: MutableLiveData<HomeList> = MutableLiveData(),
        val likedImages: MutableLiveData<HomeList> = MutableLiveData()
    )
}