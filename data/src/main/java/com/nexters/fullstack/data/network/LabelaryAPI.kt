package com.nexters.fullstack.data.network

import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

interface LabelaryAPI {
    @GET("")
    fun load(): Single<List<LabelEntity>>

    @POST("")
    fun save(labels: Pair<List<LabelEntity>, FileImageEntity>): Completable
}