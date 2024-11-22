package com.nexters.fullstack.domain.repository

import com.nexters.fullstack.domain.entity.FileImageEntity
import io.reactivex.Completable

interface AlbumRepository {
    fun getUnLabeling(pathFilter: String): List<FileImageEntity>
    fun getAll(pathFilter: String): List<FileImageEntity>
    fun delete(path: String): Completable
}