package com.nexters.fullstack.domain.local

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single


interface LabelaryLocalDataSource {
    interface Label {
        fun insertOrUpdate(label: LabelEntity): Completable
        fun delete(label: LabelEntity): Completable
        fun labelLoad(): Single<List<LabelEntity>>
        fun searchLabel(keyword: String): Single<List<LabelEntity>>
        fun loadRecentlyLabels(): Single<List<LabelEntity>>
        fun loadWithImages(): Single<List<Pair<LabelEntity, List<ImageEntity>>>>
    }

    interface Image {
        fun insertOrUpdate(image: ImageEntity): Completable
        fun insertOrUpdate(image: List<ImageEntity>): Completable
        fun delete(image: ImageEntity): Completable
        fun imageLoad(): Single<List<ImageEntity>>
        fun searchByLabels(labels: List<LabelEntity>): Single<List<ImageEntity>>
        fun searchByLabel(label: LabelEntity): Single<List<ImageEntity>>
        fun searchByLabelOrderByOldest(label: LabelEntity): Single<List<ImageEntity>>
        fun searchByLabelOrderByRecent(label: LabelEntity): Single<List<ImageEntity>>
        fun searchByLabelOrderByViewCount(label: LabelEntity): Single<List<ImageEntity>>
        fun loadByBookMark(bookmark: Boolean): Single<List<ImageEntity>>
        fun find(id: String): Single<ImageEntity>
    }
}