package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.RequestLabeling
import com.nexters.fullstack.domain.usecase.RequestUnLabeling
import com.nexters.fullstack.domain.usecase.SearchImagesBySingleLabel
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.neverError
import com.nexters.fullstack.presentaion.printSt
import com.nexters.fullstack.presentaion.takeWhen
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class AlbumViewModel constructor(
    private val getImagesByLabel: SearchImagesBySingleLabel,
    private val unLabeling: RequestUnLabeling
) : BaseViewModel(), AlbumInput, AlbumOutput {

    private val _fetchImages = PublishSubject.create<LabelEntity>()
    private val _clickRecentlyTab = PublishSubject.create<Unit>()
    private val _clickOldestTab = PublishSubject.create<Unit>()
    private val _clickItem = PublishSubject.create<ImageEntity>()
    private val _selectItem = PublishSubject.create<ImageEntity>()
    private val _clickOption = PublishSubject.create<Unit>()
    private val _clickUnLabel = PublishSubject.create<Unit>()
    private val _clickAddScreenShot = PublishSubject.create<Unit>()
    private val _clickFrequentlyTab = PublishSubject.create<Unit>()
    private val _delete = PublishSubject.create<Unit>()
    private val _requestDelete = PublishSubject.create<Unit>()
    private val _finish = PublishSubject.create<Unit>()

    //route
    private val _state = MutableLiveData<State>()
    private val _finishActivity = MutableLiveData<Unit>()
    private val _goToDetail = MutableLiveData<ImageEntity>()
    private val _showDeleteConfirmMessage = MutableLiveData<Unit>()

    override fun fetchImages(label: LabelEntity) = _fetchImages.onNext(label)
    override fun clickAddScreenShot() = _clickAddScreenShot.onNext(Unit)
    override fun clickRecentlyTab() = _clickRecentlyTab.onNext(Unit)
    override fun clickOldestTab() = _clickOldestTab.onNext(Unit)
    override fun clickFrequentlyTab() = _clickFrequentlyTab.onNext(Unit)
    override fun clickItem(image: ImageEntity) = _clickItem.onNext(image)
    override fun clickOption() = _clickOption.onNext(Unit)
    override fun clickUnLabel() = _clickUnLabel.onNext(Unit)
    override fun selectItem(image: ImageEntity) = _selectItem.onNext(image)
    override fun delete() = _delete.onNext(Unit)
    override fun requestDelete() = _requestDelete.onNext(Unit)
    override fun finish() = _finish.onNext(Unit)

    override fun state(): LiveData<State> = _state
    override fun finishActivity() = _finishActivity
    override fun showDeleteConfirmMessage() = _showDeleteConfirmMessage
    override fun goToDetail(): LiveData<ImageEntity> = _goToDetail

    init {
        val refresh = BehaviorSubject.createDefault(Unit)

        val tab = Observable.merge(
            _clickRecentlyTab.map { Tab.RECENTLY },
            _clickOldestTab.map { Tab.OLDEST },
            _clickFrequentlyTab.map { Tab.FREQUENTLY }
        ).startWith(Tab.RECENTLY)

        val fetchImages = Observable.combineLatest(
            refresh,
            _fetchImages,
            tab.map {
                when (it) {
                    Tab.RECENTLY -> SearchImagesBySingleLabel.Param.OrderType.RECENT
                    Tab.OLDEST -> SearchImagesBySingleLabel.Param.OrderType.OLDEST
                    Tab.FREQUENTLY -> SearchImagesBySingleLabel.Param.OrderType.FREQUENTLY
                }
            },
            ::Triple
        ).flatMapMaybe { (_, label, orderType) ->
            getImagesByLabel
                .buildUseCase(
                    SearchImagesBySingleLabel.Param(label, orderType)
                ).subscribeOn(Schedulers.io())
                .doOnError { it.printStackTrace() }
                .neverError()
        }.observeOn(AndroidSchedulers.mainThread())
            .share()

        val selectImage = _selectItem.scan(hashSetOf<ImageEntity>()) { set, image ->
            if (set.contains(image))
                set.remove(image)
            else set.add(image)
            set
        }.share()

        val clearImages = selectImage.takeWhen(_clickOption) { _, selectedImages ->
            selectedImages.clear()
            selectedImages
        }

        val isSelect = _clickOption.scan(false, { before, _ -> before.not() })

        val clickBack = isSelect.takeWhen(_finish) { _, isSelect -> isSelect }

        val selectedImages = Observable.merge(selectImage, clearImages)

        compositeDisposable.addAll(
            Observables.combineLatest(
                _fetchImages,
                fetchImages,
                selectedImages,
                tab,
                Observable.merge(
                    refresh.map { false },
                    isSelect
                ),
                ::State
            ).subscribe(_state::setValue, ::printSt),
            isSelect.takeWhen(_clickItem) { image, select -> image to select }
                .filter { it.second.not() }
                .map { it.first }
                .subscribe(_goToDetail::setValue),
            clickBack.filter { it.not() }
                .map { Unit }
                .subscribe(_finishActivity::setValue),
            clickBack.filter { it }
                .map { Unit }
                .subscribe(_clickOption::onNext),
            _delete.subscribe(_showDeleteConfirmMessage::setValue),
            Observable.combineLatest(
                selectedImages,
                _fetchImages,
                ::Pair
            ).takeWhen(_requestDelete) { _, data -> data }
                .filter { it.first.isNotEmpty() }
                .observeOn(Schedulers.io())
                .flatMap { (target, label) ->
                    unLabeling
                        .buildUseCase(
                            RequestUnLabeling.RequestParam(
                                target.toList(),
                                label
                            )
                        ).doOnError { it.printStackTrace() }
                        .neverError()
                        .andThen(Observable.just(Unit))
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe(refresh::onNext)
        )
    }


    data class State(
        val label: LabelEntity,
        val images: List<ImageEntity>,
        val selectedImages: Set<ImageEntity>,
        val tab: Tab,
        val isSelect: Boolean
    ) {
        val title = if (isSelect)
            if (selectedImages.isNotEmpty())
                "${selectedImages.size}개"
            else ""
        else label.text

        val showEmptyView = images.isEmpty()
        val isEnableDeleteButton = selectedImages.isNotEmpty()
        val isSelectRecently = if (tab == Tab.RECENTLY) label.color else null
        val isSelectOldest = if (tab == Tab.OLDEST) label.color else null
        val isSelectFrequently = if (tab == Tab.FREQUENTLY) label.color else null
    }

    enum class Tab {
        RECENTLY, OLDEST, FREQUENTLY
    }
}


interface AlbumInput : AlbumListInput {
    fun fetchImages(label: LabelEntity)
    fun clickRecentlyTab()
    fun clickOldestTab()
    fun clickFrequentlyTab()
    fun clickOption()
    fun clickUnLabel()
    fun delete()
    fun finish()
    fun requestDelete()
}

interface AlbumListInput {
    fun clickItem(image: ImageEntity)
    fun selectItem(image: ImageEntity)
    fun clickAddScreenShot()
}

interface AlbumOutput {
    fun finishActivity(): LiveData<Unit>
    fun goToDetail(): LiveData<ImageEntity>
    fun showDeleteConfirmMessage(): LiveData<Unit>
    fun state(): LiveData<AlbumViewModel.State>
}