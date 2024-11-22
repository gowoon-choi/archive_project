package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class GetUnlabeledImages(
    private val albumRepository: AlbumRepository,
    private val imageRepository: ImageRepository
) : BaseUseCase<String, Single<List<FileImageEntity>>> {
    override fun buildUseCase(params: String): Single<List<FileImageEntity>> {
        val images = albumRepository.getUnLabeling(params)
        return imageRepository.load()
            .map { dbImages ->
                images.filter { localImage ->
                    dbImages.find {
                        it.image.id == localImage.id
                    } == null
                }
            }
    }
}