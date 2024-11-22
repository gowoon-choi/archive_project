package com.nexters.fullstack.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.nexters.fullstack.data.db.TableName
import com.nexters.fullstack.domain.entity.ImageEntity

@Entity(tableName = TableName.LABEL_IMAGE_REF, primaryKeys = ["imageId", "labelId"])
data class LabelingRelationRef(
    val imageId: String,
    val labelId: Long
)

data class LabelWithImages(
    @Embedded val label: LabelModel,
    @Relation(
        parentColumn = "labelId",
        entityColumn = "imageId",
        associateBy = Junction(LabelingRelationRef::class)
    )
    val images: List<ImageModel>
)

data class ImageWithLabels(
    @Embedded val image: ImageModel,
    @Relation(
        parentColumn = "imageId",
        entityColumn = "labelId",
        associateBy = Junction(LabelingRelationRef::class)
    )
    val labels: List<LabelModel>
)
