package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

private const val SCREEN_SHOT_PREFIX = "Screenshots"

class GetRecentlyImageData(
    private val albumRepository: AlbumRepository,
    private val imageRepository: ImageRepository
) : BaseUseCase<Unit, Single<GetRecentlyImageData.Result>> {

    override fun buildUseCase(params: Unit): Single<Result> {
        return Single.zip(
            Single.fromCallable { albumRepository.getAll(SCREEN_SHOT_PREFIX) },
            imageRepository.load(),
            ::Result
        )
    }


    data class Result(
        val localImages: List<FileImageEntity>,
        val dbImages: List<ImageEntity>
    )
}