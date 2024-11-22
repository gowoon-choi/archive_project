package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class RequestUnLabeling(
    private val imageRepository: ImageRepository
) : BaseUseCase<RequestUnLabeling.RequestParam, Completable> {
    override fun buildUseCase(params: RequestParam): Completable {

        return imageRepository.insertOrUpdate(
            params.targets.map {
                it.copy(labels = it.labels.filter {
                    it.id != params.label.id
                })
            }
        )
    }


    data class RequestParam(
        val targets: List<ImageEntity>,
        val label: LabelEntity
    )
}