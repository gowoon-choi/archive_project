package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class GetSearchHomeData(
    private val labelRepository: LabelRepository
) : BaseUseCase<Unit, Single<GetSearchHomeData.Result>> {

    override fun buildUseCase(params: Unit): Single<Result> {
        return Single.zip(
            labelRepository.loadAll(),
            labelRepository.loadRecentlyLabels(),
            ::Result
        )
    }


    data class Result(
        val labels : List<LabelEntity>,
        val recentlyLabels : List<LabelEntity>
    )
}