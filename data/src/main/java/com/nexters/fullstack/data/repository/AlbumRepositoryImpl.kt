package com.nexters.fullstack.data.repository

import com.nexters.fullstack.data.local.filesystem.FileSystemImages
import com.nexters.fullstack.data.mapper.FileImageMapper
import com.nexters.fullstack.domain.repository.AlbumRepository
import com.nexters.fullstack.data.model.FileImage
import com.nexters.fullstack.domain.entity.FileImageEntity
import io.reactivex.Completable


internal class AlbumRepositoryImpl(private val fileSystemImages: FileSystemImages) : AlbumRepository {

    override fun getUnLabeling(pathFilter: String): List<FileImageEntity> {
        return fileSystemImages.fetch(SCREENSHOT_POSTFIX).map { filePath ->
            FileImageMapper.fromData(
                FileImage(id = filePath, originUrl = filePath)
            )
        }.distinctBy { it.id }
    }

    override fun getAll(pathFilter: String): List<FileImageEntity> {
        return fileSystemImages.fetch(SCREENSHOT_POSTFIX).map { filePath ->
            FileImageMapper.fromData(
                FileImage(id = filePath, originUrl = filePath)
            )
        }.distinctBy { it.id }
    }

    override fun delete(path: String): Completable {
        return fileSystemImages.delete(path)
    }

    companion object {
        private const val SCREENSHOT_POSTFIX = "Screenshots"
        private const val DOWNLOAD_POSTFIX = "Download"
    }
}