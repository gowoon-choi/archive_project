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

class GetDetailImage(
    private val albumRepository: AlbumRepository,
    private val imageRepository: ImageRepository
) : BaseUseCase<String, Single<GetDetailImage.Result>> {

    override fun buildUseCase(params: String): Single<Result> {
        return Single.fromCallable {
            albumRepository.getAll(SCREEN_SHOT_PREFIX).first { it.id == params }
        }.flatMap { local ->
            imageRepository.find(params)
                .map { Result(local, it) }
                .onErrorReturn { Result(local, null) }
        }
    }


    data class Result(
        val fileImageEntity: FileImageEntity,
        val imageEntity: ImageEntity?
    )
}