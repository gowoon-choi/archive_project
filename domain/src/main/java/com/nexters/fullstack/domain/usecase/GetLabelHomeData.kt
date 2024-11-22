package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class GetLabelHomeData(
    private val labelRepository: LabelRepository
) : BaseUseCase<Unit, Single<GetLabelHomeData.Result>> {
    override fun buildUseCase(params: Unit): Single<Result> {
        return labelRepository.loadWithImages().map {
            Result(
                it.map { (label, images) ->
                    Triple(label, images.firstOrNull(), images.size)
                }
            )
        }
    }


    data class Result(
        val labels: List<Triple<LabelEntity, ImageEntity?, Int>>,
    )
}