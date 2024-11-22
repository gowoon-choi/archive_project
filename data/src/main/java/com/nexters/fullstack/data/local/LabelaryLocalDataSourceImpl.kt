package com.nexters.fullstack.data.local

import com.nexters.fullstack.data.db.dao.ImageDAO
import com.nexters.fullstack.data.db.dao.LabelDAO
import com.nexters.fullstack.data.db.dao.LabelingDAO
import com.nexters.fullstack.data.db.entity.LabelingRelationRef
import com.nexters.fullstack.data.mapper.ImageModelMapper
import com.nexters.fullstack.data.mapper.ImageWitLabelsMapper
import com.nexters.fullstack.data.mapper.LabelModelMapper
import com.nexters.fullstack.domain.local.LabelaryLocalDataSource
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single

class
LabelaryLocalDataSourceImpl(
    private val labelDAO: LabelDAO,
    private val imageDAO: ImageDAO,
    private val labelingDao: LabelingDAO
) : LabelaryLocalDataSource.Label, LabelaryLocalDataSource.Image {
    override fun insertOrUpdate(label: LabelEntity): Completable {
        return labelDAO.insertOrUpdate(LabelModelMapper.toData(label))
    }

    override fun delete(label: LabelEntity): Completable {
        return labelDAO.delete(LabelModelMapper.toData(label))
    }

    override fun labelLoad(): Single<List<LabelEntity>> {
        return labelDAO.load().map { labels ->
            labels.map(LabelModelMapper::fromData)
        }
    }

    override fun loadWithImages(): Single<List<Pair<LabelEntity, List<ImageEntity>>>> {
        Int
        return labelDAO.loadWithImages().map { labels ->
            labels.map { LabelModelMapper.fromData(it.label) to it.images.map(ImageModelMapper::toData) }
        }
    }

    override fun loadRecentlyLabels(): Single<List<LabelEntity>> {
        return labelingDao.loadRecentlyLabels().map { labels ->
            labels.map(LabelModelMapper::fromData)
        }
    }

    override fun searchLabel(keyword: String): Single<List<LabelEntity>> {
        return labelDAO.searchLabels(keyword).map {
            it.map(LabelModelMapper::fromData)
        }
    }

    override fun insertOrUpdate(image: List<ImageEntity>): Completable {
        return Completable.concat(image.map { insertOrUpdate(it) })
    }

    override fun insertOrUpdate(image: ImageEntity): Completable {
        return imageDAO.insertOrUpdate(ImageWitLabelsMapper.fromData(image).image)
            .andThen(labelingDao.delete(arrayOf(image.image.id)))
            .andThen(labelingDao.insertOrUpdate(
                ImageWitLabelsMapper.fromData(image).run {
                    labels.map {
                        LabelingRelationRef(imageId = this.image.imageId, labelId = it.labelId)
                    }
                }
            ))
    }

    override fun delete(image: ImageEntity): Completable {
        return imageDAO.delete(ImageWitLabelsMapper.fromData(image).image)
    }

    override fun imageLoad(): Single<List<ImageEntity>> {
        return imageDAO.load().map { userImages ->
            userImages.map { ImageWitLabelsMapper.toData(it) }
        }
    }

    override fun loadByBookMark(bookmark: Boolean): Single<List<ImageEntity>> {
        return (if (bookmark) imageDAO.loadLikes() else imageDAO.loadUnLikes()).map { userImages ->
            userImages.map { ImageWitLabelsMapper.toData(it) }
        }
    }

    override fun find(id: String): Single<ImageEntity> {
        return imageDAO.find(id).map {
            ImageWitLabelsMapper.toData(it)
        }
    }

    override fun searchByLabels(labels: List<LabelEntity>): Single<List<ImageEntity>> {
        return imageDAO.searchByLabels(labels.map { it.id }).map { userImages ->
            userImages.map { ImageWitLabelsMapper.toData(it) }
        }
    }

    override fun searchByLabel(label: LabelEntity): Single<List<ImageEntity>> {
        return imageDAO.searchByLabel(label.id).map { userImages ->
            userImages.map(ImageWitLabelsMapper::toData)
        }
    }

    override fun searchByLabelOrderByOldest(label: LabelEntity): Single<List<ImageEntity>> {
        return imageDAO.searchByLabelWithOrderByOldest(label.id).map { userImages ->
            userImages.map(ImageWitLabelsMapper::toData)
        }
    }

    override fun searchByLabelOrderByRecent(label: LabelEntity): Single<List<ImageEntity>> {
        return imageDAO.searchByLabelWithOrderByRecent(label.id).map { userImages ->
            userImages.map(ImageWitLabelsMapper::toData)
        }
    }

    override fun searchByLabelOrderByViewCount(label: LabelEntity): Single<List<ImageEntity>> {
        return imageDAO.searchByLabelWithOrderByViewCount(label.id).map { userImages ->
            userImages.map(ImageWitLabelsMapper::toData)
        }
    }
}
