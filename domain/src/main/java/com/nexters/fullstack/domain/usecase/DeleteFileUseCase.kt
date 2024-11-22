package com.nexters.fullstack.domain.usecase

import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.domain.usecase.base.BaseUseCase
import io.reactivex.Completable

class DeleteFileUseCase(
    private val albumRepository: AlbumRepository
) : BaseUseCase<String, Completable> {

    override fun buildUseCase(params: String): Completable {
        return albumRepository.delete(params)
    }

}
