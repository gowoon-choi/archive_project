package com.nexters.fullstack.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.nexters.feature.BR
import com.nexters.feature.util.ColorUtils
import com.nexters.fullstack.R
import com.nexters.fullstack.databinding.ItemAlbumWithLabelBinding
import com.nexters.fullstack.presentaion.model.LabelingImage

class LocalImageViewHolder(
    private val binding: ItemAlbumWithLabelBinding,
    private val onClick: Any?,
    private val delegate: Any?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: LabelingImage) {
        binding.labelingImage = item
        binding.image = item.localImages.first()
        binding.setVariable(BR.event, onClick)
        binding.setVariable(BR.delegate, delegate)
        binding.label.background.setTint(
            ColorUtils(
                item.domainLabel.color ?: "",
                binding.root.context
            ).getActive()
        )
        binding.label.setTextColor(
            ColorUtils(
                item.domainLabel.color ?: "",
                binding.root.context
            ).getText()
        )
        binding.imageSize.text =
            binding.root.context.getString(R.string.image_count, item.localImages.size)
        binding.executePendingBindings()
    }
}