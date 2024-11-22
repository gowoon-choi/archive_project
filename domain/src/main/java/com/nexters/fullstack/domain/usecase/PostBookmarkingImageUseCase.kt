package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class PostBookmarkingImageUseCase(
    private val imageRepository: ImageRepository
) : BaseUseCase<PostBookmarkingImageUseCase.Request, Completable> {

    override fun buildUseCase(params: Request): Completable {
        return imageRepository.insertOrUpdate(params.image)
    }


    data class Request(
        val image: ImageEntity,
    )

}