package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class UpdateLabel(private val labelRepository: LabelRepository) :
    BaseUseCase<LabelEntity, Completable> {

    override fun buildUseCase(params: LabelEntity): Completable {
        return labelRepository.insertOrUpdate(params)
    }
}