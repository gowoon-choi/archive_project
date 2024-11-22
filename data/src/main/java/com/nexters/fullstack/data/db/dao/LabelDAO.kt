package com.nexters.fullstack.data.db.dao

import androidx.room.*
import com.nexters.fullstack.data.db.entity.LabelModel
import com.nexters.fullstack.data.db.entity.LabelWithImages
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LabelDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(label: LabelModel): Completable

    @Delete
    fun delete(label: LabelModel): Completable

    @Query("select * from userLabel")
    fun load(): Single<List<LabelModel>>

    @Transaction
    @Query("select * from userLabel")
    fun loadWithImages(): Single<List<LabelWithImages>>

    @Query("select * from userLabel where text LIKE '%' || :query || '%'")
    fun searchLabels(query: String): Single<List<LabelModel>>

    @Transaction
    @Query("SELECT * FROM userLabel where labelId IN (:labelIds)")
    fun loadImages(labelIds: List<Long>): List<LabelWithImages>
}