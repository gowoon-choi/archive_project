package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class RequestBookmarkingImages(
    private val imageRepository: ImageRepository
) : BaseUseCase<RequestBookmarkingImages.Request, Completable> {
    override fun buildUseCase(params: Request): Completable {
        return imageRepository.insertOrUpdate(params.images.map { it.copy(liked = params.bookmark) })
    }


    data class Request(
        val images : List<ImageEntity>,
        val bookmark : Boolean
    )
}