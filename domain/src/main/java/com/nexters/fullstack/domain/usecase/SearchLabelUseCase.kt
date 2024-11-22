package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.LabelRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class SearchLabelUseCase(
    private val labelRepository: LabelRepository
) : BaseUseCase<String, Single<List<LabelEntity>>> {

    override fun buildUseCase(params: String): Single<List<LabelEntity>> {
        return labelRepository.searchLabel(params)
    }
}