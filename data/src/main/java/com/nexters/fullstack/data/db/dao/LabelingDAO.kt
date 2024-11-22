package com.nexters.fullstack.data.db.dao

import androidx.room.*
import com.nexters.fullstack.data.db.entity.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LabelingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(relations: List<LabelingRelationRef>): Completable

    @Transaction
    @Query("select * from userLabel WHERE labelId in (SELECT DISTINCT labelId FROM labelImageRef LIMIT 6)")
    fun loadRecentlyLabels(): Single<List<LabelModel>>

    @Transaction
    @Query("delete from labelImageRef where imageId IN (:ids)")
    fun delete(ids : Array<String>): Completable
}