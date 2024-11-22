package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class DeleteLabels(
    private val labelRepository: LabelRepository
) : BaseUseCase<List<LabelEntity>, Completable> {
    override fun buildUseCase(params: List<LabelEntity>): Completable {
        return labelRepository.delete(params)
    }
}