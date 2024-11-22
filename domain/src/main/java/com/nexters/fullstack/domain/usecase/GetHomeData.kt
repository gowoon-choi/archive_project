package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

private const val SCREEN_SHOT_PREFIX = "Screenshots"

class GetHomeData(
    private val albumRepository: AlbumRepository,
    private val imageRepository: ImageRepository
) : BaseUseCase<Unit, Single<GetHomeData.Result>> {

    override fun buildUseCase(params: Unit): Single<Result> {
        return Single.zip(
            Single.fromCallable { albumRepository.getAll(SCREEN_SHOT_PREFIX) },
            imageRepository.loadByBookmark(true),
            ::Result
        )
    }


    data class Result(
        val recentlyImages: List<FileImageEntity>,
        val bookmarkingImages: List<ImageEntity>
    )
}