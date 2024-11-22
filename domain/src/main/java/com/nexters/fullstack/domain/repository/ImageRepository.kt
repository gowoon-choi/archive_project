package com.nexters.fullstack.domain.repository

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface ImageRepository {
    fun insertOrUpdate(data: ImageEntity): Completable

    fun insertOrUpdate(data: List<ImageEntity>): Completable

    fun delete(data: ImageEntity): Completable

    fun delete(data: List<ImageEntity>): Completable

    fun load(): Single<List<ImageEntity>>

    fun loadByBookmark(bookmark : Boolean): Single<List<ImageEntity>>

    fun searchByLabels(labels : List<LabelEntity>): Single<List<ImageEntity>>

    fun searchByLabel(labels: LabelEntity): Single<List<ImageEntity>>

    fun searchByLabelOrderByViewCount(labels: LabelEntity): Single<List<ImageEntity>>

    fun searchByLabelOrderByOldest(labels: LabelEntity): Single<List<ImageEntity>>

    fun searchByLabelOrderByRecent(labels: LabelEntity): Single<List<ImageEntity>>

    fun find(id: String): Single<ImageEntity>
}