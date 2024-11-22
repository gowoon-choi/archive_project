package com.nexters.fullstack.data.remote

import com.nexters.fullstack.data.network.LabelaryAPI
import com.nexters.fullstack.domain.remote.LabelaryRemoteDataSource
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single

internal class LabelaryRemoteDataSourceImpl(private val labelaryAPI: LabelaryAPI) :
    LabelaryRemoteDataSource {
    override fun load(): Single<List<LabelEntity>> {
        return labelaryAPI.load()
    }


    /***
     * 이건 의견이 필요해요 ! 중위 함수를 사용해서 사진에 대한 데이터와 라벨을 Pair로 넘겨주는게 좋을지
     * 혹은 매개변수를 2개( 라벨리스트, 이미지 데이터 )를 받는게 더 코드 리딩하는게 좋을지
     * 우선 pair 로 넘겨주었어요 ~
     ***/
    override fun save(labels: Pair<List<LabelEntity>, FileImageEntity>): Completable {
        return labelaryAPI.save(labels)
    }
}