package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class RejctLabeling(
    private val imageRepository: ImageRepository
) : BaseUseCase<ImageEntity, Completable> {
    override fun buildUseCase(params: ImageEntity): Completable {
        return imageRepository.insertOrUpdate(params)
    }
}