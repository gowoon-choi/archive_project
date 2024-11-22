package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class SearchImagesByLabel(
    private val imageRepository: ImageRepository
) : BaseUseCase<List<LabelEntity>, Single<List<ImageEntity>>> {

    override fun buildUseCase(params: List<LabelEntity>): Single<List<ImageEntity>> {
        return imageRepository.searchByLabels(params)
    }
}