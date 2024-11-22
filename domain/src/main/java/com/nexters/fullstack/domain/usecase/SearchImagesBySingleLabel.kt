package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.repository.ImageRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Single

class SearchImagesBySingleLabel constructor(
    private val imageRepository: ImageRepository
) : BaseUseCase<SearchImagesBySingleLabel.Param, Single<List<ImageEntity>>> {

    override fun buildUseCase(params: Param): Single<List<ImageEntity>> {
        return when(params.orderType){
            Param.OrderType.RECENT -> imageRepository.searchByLabelOrderByRecent(params.labelEntity)
            Param.OrderType.OLDEST -> imageRepository.searchByLabelOrderByOldest(params.labelEntity)
            Param.OrderType.FREQUENTLY -> imageRepository.searchByLabelOrderByViewCount(params.labelEntity)
        }
    }


    data class Param(
        val labelEntity: LabelEntity,
        val orderType: OrderType
    ) {
        enum class OrderType {
            RECENT, OLDEST, FREQUENTLY
        }
    }
}
