package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class LoadImageUseCase(private val labelaryRepository: ImageRepository) :
    BaseUseCase<Unit, Single<List<ImageEntity>>> {
    override fun buildUseCase(params: Unit): Single<List<ImageEntity>> {
        return labelaryRepository.load()
    }
}