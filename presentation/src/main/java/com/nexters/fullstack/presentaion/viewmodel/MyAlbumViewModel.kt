package com.nexters.fullstack.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.DeleteLabels
import com.nexters.fullstack.domain.usecase.GetLabelHomeData
import com.nexters.fullstack.presentaion.BaseViewModel
import com.nexters.fullstack.presentaion.Input
import com.nexters.fullstack.presentaion.Output
import com.nexters.fullstack.presentaion.neverError
import com.nexters.fullstack.util.SingleLiveData
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MyAlbumViewModel(
    private val getLabelHomeData: GetLabelHomeData,
    private val deleteLabels: DeleteLabels,
) : BaseViewModel(), MyAlbumInput, MyAlbumOutput {
    // Input
    private val refresh = PublishSubject.create<Unit>()
    private val createNewLabel = PublishSubject.create<Unit>()
    private val clickLabel = PublishSubject.create<LabelEntity>()
    private val updateLabel = PublishSubject.create<LabelEntity>()
    private val deleteLabel = PublishSubject.create<LabelEntity>()
    private val successLabelEdit = PublishSubject.create<Unit>()
    override fun refresh() = refresh.onNext(Unit)
    override fun createNewLabel() = createNewLabel.onNext(Unit)
    override fun clickLabel(label: LabelEntity) = clickLabel.onNext(label)
    override fun updateLabel(label: LabelEntity) = updateLabel.onNext(label)
    override fun deleteLabel(label: LabelEntity) = deleteLabel.onNext(label)
    override fun successLabelEdit() = successLabelEdit.onNext(Unit)

    // Output
    private val state = MutableLiveData<MyAlbumState>()
    private val showErrorToast = SingleLiveData<Unit>()
    private val showDeleteSuccessToast = SingleLiveData<Unit>()
    private val goToLabelDetail = SingleLiveData<LabelEntity>()
    private val goToLabelEdit = SingleLiveData<LabelEntity?>()
    override fun state(): LiveData<MyAlbumState> = state
    override fun showErrorToast(): SingleLiveData<Unit> = showErrorToast
    override fun showDeleteSuccessToast(): SingleLiveData<Unit> = showDeleteSuccessToast
    override fun goToLabelDetail(): SingleLiveData<LabelEntity> = goToLabelDetail
    override fun goToLabelEdit(): SingleLiveData<LabelEntity?> = goToLabelEdit

    init {
        val deleteLabel = deleteLabel
            .observeOn(Schedulers.io())
            .flatMapSingle {
                deleteLabels.buildUseCase(listOf(it))
                    .doOnError {
                        showErrorToast.value = Unit
                    }.neverError()
                    .andThen(Single.just(Unit))
            }.observeOn(AndroidSchedulers.mainThread())
            .share()

        val labelHomeData = Observable.merge(successLabelEdit, refresh, deleteLabel)
            .observeOn(Schedulers.io())
            .flatMapMaybe {
                getLabelHomeData.buildUseCase(Unit)
                    .doOnError {
                        showErrorToast.value = Unit
                    }.neverError()
            }.observeOn(AndroidSchedulers.mainThread())
            .share()

        compositeDisposable.addAll(
            labelHomeData.map { MyAlbumState(it.labels) }
                .subscribe(state::postValue),
            createNewLabel.subscribe { goToLabelEdit.value = null },
            updateLabel.subscribe(goToLabelEdit::setValue),
            clickLabel.subscribe(goToLabelDetail::setValue),
            deleteLabel.subscribe(showDeleteSuccessToast::setValue),
        )
    }
}

data class MyAlbumState(
    val labels: List<Triple<LabelEntity, ImageEntity?, Int>>,
) {
    val showShowEmptyView: Boolean = labels.isEmpty()
}

interface MyAlbumInput : Input {
    fun refresh()
    fun createNewLabel()
    fun clickLabel(label: LabelEntity)
    fun updateLabel(label: LabelEntity)
    fun deleteLabel(label: LabelEntity)
    fun successLabelEdit()
}

interface MyAlbumOutput : Output {
    fun state(): LiveData<MyAlbumState>
    fun showErrorToast(): SingleLiveData<Unit>
    fun showDeleteSuccessToast(): SingleLiveData<Unit>
    fun goToLabelDetail(): SingleLiveData<LabelEntity>
    fun goToLabelEdit(): SingleLiveData<LabelEntity?>
}
