package com.nexters.fullstack.data.db.dao

import androidx.room.*
import com.nexters.fullstack.data.db.entity.ImageModel
import com.nexters.fullstack.data.db.entity.ImageWithLabels
import com.nexters.fullstack.data.db.entity.LabelModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(image: ImageModel): Completable

    @Transaction
    @Query("select * from userImage")
    fun load(): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage WHERE imageId in (SELECT labelId FROM labelImageRef where labelId IN (:labelIds))")
    fun searchByLabels(labelIds: List<Long>): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage WHERE imageId in (SELECT imageId FROM labelImageRef where labelId == :labelId)")
    fun searchByLabel(labelId: Long): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage WHERE imageId in (SELECT imageId FROM labelImageRef where labelId == :labelId)  ORDER BY viewCount DESC")
    fun searchByLabelWithOrderByViewCount(labelId: Long): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage WHERE imageId in (SELECT imageId FROM labelImageRef where labelId == :labelId)  ORDER BY createdAt ASC")
    fun searchByLabelWithOrderByOldest(labelId: Long): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage WHERE imageId in (SELECT imageId FROM labelImageRef where labelId == :labelId)  ORDER BY createdAt DESC")
    fun searchByLabelWithOrderByRecent(labelId: Long): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage where liked == 1")
    fun loadLikes(): Single<List<ImageWithLabels>>

    @Transaction
    @Query("select * from userImage where liked == 0")
    fun loadUnLikes(): Single<List<ImageWithLabels>>

    @Delete
    fun delete(image: ImageModel): Completable

    @Transaction
    @Query("select * from userImage where imageId == :id")
    fun find(id: String): Single<ImageWithLabels>
}