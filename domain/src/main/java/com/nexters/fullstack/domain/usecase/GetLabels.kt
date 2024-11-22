package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class GetLabels(private val labelRepository: LabelRepository) :
    BaseUseCase<Unit, Single<List<LabelEntity>>> {
    override fun buildUseCase(params: Unit): Single<List<LabelEntity>> {
        return labelRepository.loadAll()
    }
}