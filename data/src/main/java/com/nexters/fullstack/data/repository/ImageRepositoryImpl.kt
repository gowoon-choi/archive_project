package com.nexters.fullstack.data.repository

import com.nexters.fullstack.domain.local.LabelaryLocalDataSource
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.SingleSource

class ImageRepositoryImpl(private val labelaryLocalDataSource: LabelaryLocalDataSource.Image) :
    ImageRepository {
    override fun insertOrUpdate(data: ImageEntity): Completable {
        return labelaryLocalDataSource.insertOrUpdate(data)
    }

    override fun insertOrUpdate(data: List<ImageEntity>): Completable {
        return labelaryLocalDataSource.insertOrUpdate(data)
    }

    override fun delete(data: ImageEntity): Completable {
        return labelaryLocalDataSource.delete(data)
    }

    override fun delete(data: List<ImageEntity>): Completable =
        Completable.concat(data.map(::delete))

    override fun load(): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.imageLoad()
    }

    override fun find(id: String): Single<ImageEntity> {
        return labelaryLocalDataSource.find(id)
    }

    override fun loadByBookmark(bookmark: Boolean): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.loadByBookMark(bookmark)
    }

    override fun searchByLabels(labels: List<LabelEntity>): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.searchByLabels(labels)
    }

    override fun searchByLabel(labels: LabelEntity): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.searchByLabel(labels)
    }

    override fun searchByLabelOrderByViewCount(labels: LabelEntity): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.searchByLabelOrderByViewCount(labels)
    }

    override fun searchByLabelOrderByOldest(labels: LabelEntity): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.searchByLabelOrderByOldest(labels)
    }

    override fun searchByLabelOrderByRecent(labels: LabelEntity): Single<List<ImageEntity>> {
        return labelaryLocalDataSource.searchByLabelOrderByRecent(labels)
    }
}