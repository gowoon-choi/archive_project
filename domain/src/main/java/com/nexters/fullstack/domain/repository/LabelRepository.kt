package com.nexters.fullstack.domain.repository

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LabelRepository {
    fun insertOrUpdate(label: LabelEntity): Completable

    fun delete(label: LabelEntity): Completable

    fun delete(labels: List<LabelEntity>): Completable

    fun loadAll(): Single<List<LabelEntity>>

    fun loadRecentlyLabels(): Single<List<LabelEntity>>

    fun searchLabel(keyword: String): Single<List<LabelEntity>>

    fun loadWithImages(): Single<List<Pair<LabelEntity, List<ImageEntity>>>>
}