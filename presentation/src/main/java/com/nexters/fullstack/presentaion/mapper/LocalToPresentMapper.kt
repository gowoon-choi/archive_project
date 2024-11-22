package com.nexters.fullstack.presentaion.mapper

import com.nexters.fullstack.domain.Mapper
import com.nexters.fullstack.presentaion.model.FileImageViewData
import com.nexters.fullstack.domain.entity.FileImageEntity

object LocalToPresentMapper : Mapper<FileImageEntity, FileImageViewData> {
    override fun toData(data: FileImageEntity): FileImageViewData {
        return FileImageViewData(data.originUrl, data.timeStamp)
    }

    override fun fromData(data: FileImageViewData): FileImageEntity {
        return FileImageEntity( id = data.url, originUrl = data.url, timeStamp = data.timeStamp)
    }
}