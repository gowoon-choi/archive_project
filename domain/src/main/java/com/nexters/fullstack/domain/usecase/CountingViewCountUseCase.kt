package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class CountingViewCountUseCase(
    private val imageRepository: ImageRepository
) : BaseUseCase<CountingViewCountUseCase.Param, Completable> {

    override fun buildUseCase(params: Param): Completable {
        return imageRepository.insertOrUpdate(params.imageEntity.copy(viewCount = params.imageEntity.viewCount + 1))
    }

    data class Param(
        val imageEntity: ImageEntity
    )
}