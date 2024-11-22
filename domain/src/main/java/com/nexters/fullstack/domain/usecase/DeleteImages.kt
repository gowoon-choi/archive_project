package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class DeleteImages(
    private val imageRepository: ImageRepository
) : BaseUseCase<List<ImageEntity>, Completable> {

    override fun buildUseCase(params: List<ImageEntity>): Completable {
        return imageRepository.delete(params)
    }
}