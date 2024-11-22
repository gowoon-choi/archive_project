package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.domain.entity.LabelSwipeState
import com.nexters.fullstack.presentaion.model.*
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.usecase.GetUnlabeledImages
import com.nexters.fullstack.domain.usecase.RejctLabeling
import com.nexters.fullstack.presentaion.*
import com.nexters.fullstack.presentaion.mapper.FileImageViewDataToEntityMapper
import com.nexters.fullstack.presentaion.mapper.PresenterLocalFileMapper
import com.nexters.fullstack.util.SingleLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MainViewModel(
    getUnlabeledImages: GetUnlabeledImages,
    rejctLabeling: RejctLabeling,
    mapper: Mapper<FileImageEntity, FileImageViewData>
) : BaseViewModel() {

    private val disposable = CompositeDisposable()
    private val currentImage = PublishSubject.create<FileImageViewData>()
    private val flipStateSubject = PublishSubject.create<LabelSwipeState>()
    private val clickButton = PublishSubject.create<MainLabelState>()
    private val refreshImages = PublishSubject.create<Unit>()

    private val startLabeling = SingleLiveData<FileImageViewData>()
    private val state = MutableLiveData<State>()


    val input = object : MainInput {
        override fun currentImage(current: FileImageViewData) = currentImage.onNext(current)
        override fun refreshImages() = refreshImages.onNext(Unit)
        override fun clickButton(state: MainLabelState) = clickButton.onNext(state)
        override fun swipe(state: LabelSwipeState) = flipStateSubject.onNext(state)
    }

    val output = object : MainOutput {
        override fun state() = state
        override fun startLabelling() = startLabeling
    }

    init {
        val loadImages = refreshImages
            .observeOn(Schedulers.io())
            .flatMapSingle {
                getUnlabeledImages.buildUseCase(SCREEN_SHOT_PRIFIX)
                    .map {
                        it.map { localLabel ->
                            mapper.toData(localLabel.copy(timeStamp = System.currentTimeMillis()))
                        }
                    }
            }.map(::State)
            .share()

        val reject = currentImage.takeWhen(
            Observable.merge(
                flipStateSubject.ofType(LabelSwipeState.Reject::class.java),
                clickButton.ofType(MainLabelState.Reject::class.java)
            )
        ) { _, currentImage ->
            FileImageViewDataToEntityMapper.toData(
                PresenterLocalFileMapper.toData(currentImage)
            )
        }.observeOn(Schedulers.io()).flatMap {
            rejctLabeling.buildUseCase(it)
                .andThen(Observable.just(Unit))
        }

        disposable.addAll(
            currentImage
                .takeWhen(
                    Observable
                        .merge(
                            flipStateSubject.ofType(LabelSwipeState.Approve::class.java),
                            clickButton.ofType(MainLabelState.Approve::class.java)
                        )
                ) { _, currentImage -> currentImage }
                .subscribe(startLabeling::postValue, ::printSt),
            Observable
                .merge(
                    loadImages,
                    reject.map { State(state.value?.images?.drop(1) ?: emptyList()) }
                ).subscribe(state::postValue, ::printSt),
        )
    }


    interface MainInput : Input {
        fun refreshImages()
        fun currentImage(current: FileImageViewData)
        fun clickButton(state: MainLabelState)
        fun swipe(state: LabelSwipeState)
    }

    interface MainOutput : Output {
        fun state(): LiveData<State>

        //Router
        fun startLabelling(): SingleLiveData<FileImageViewData>
    }

    data class State(
        val images: List<FileImageViewData>
    )

    companion object {
        private const val SCREEN_SHOT_PRIFIX = "Screenshots"
    }
}