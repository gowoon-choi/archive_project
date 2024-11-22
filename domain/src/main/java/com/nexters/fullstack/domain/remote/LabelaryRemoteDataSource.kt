package com.nexters.fullstack.domain.remote

import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single

interface LabelaryRemoteDataSource {
    fun load(): Single<List<LabelEntity>>

    fun save(labels: Pair<List<LabelEntity>, FileImageEntity>): Completable
}