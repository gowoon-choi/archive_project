package com.nexters.fullstack.data.repository

import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.local.LabelaryLocalDataSource
import com.nexters.fullstack.domain.repository.LabelRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class LabelRepositoryImpl(
    private val labelaryLocalDataSourceLabel: LabelaryLocalDataSource.Label
) : LabelRepository {

    override fun insertOrUpdate(label: LabelEntity): Completable {
        return labelaryLocalDataSourceLabel.insertOrUpdate(label)
    }

    override fun searchLabel(keyword: String): Single<List<LabelEntity>> {
        return labelaryLocalDataSourceLabel.searchLabel(keyword)
    }

    override fun delete(label: LabelEntity): Completable {
        return labelaryLocalDataSourceLabel.delete(label)
    }

    override fun delete(labels: List<LabelEntity>): Completable =
        Completable.concat(labels.map(::delete))

    override fun loadAll(): Single<List<LabelEntity>> {
        return labelaryLocalDataSourceLabel.labelLoad()
    }

    override fun loadWithImages(): Single<List<Pair<LabelEntity, List<ImageEntity>>>> {
        return labelaryLocalDataSourceLabel.loadWithImages()
    }

    override fun loadRecentlyLabels(): Single<List<LabelEntity>> {
        return labelaryLocalDataSourceLabel.loadRecentlyLabels()
    }
}