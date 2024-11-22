package com.nexters.fullstack.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseActivity
import com.nexters.fullstack.data.mapper.ImageModelMapper
import com.nexters.fullstack.databinding.ActivityAlbumActivitybyColorBinding
import com.nexters.fullstack.databinding.ItemLabelDetailItemBinding
import com.nexters.fullstack.databinding.ItemLabelDetailPlusItemBinding
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.mapper.LocalMainLabelMapper
import com.nexters.fullstack.presentaion.model.LabelViewData
import com.nexters.fullstack.presentaion.viewmodel.AlbumListInput
import com.nexters.fullstack.presentaion.viewmodel.AlbumViewModel
import com.nexters.fullstack.ui.activity.detail.DetailAlbumActivity
import com.nexters.fullstack.util.ColorUtil
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.util.Constants.DETAIL_IMAGE
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumActivityByColor : BaseActivity<ActivityAlbumActivitybyColorBinding, AlbumViewModel>() {
    override val layoutRes: Int = R.layout.activity_album_activityby_color
    override val viewModel: AlbumViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind {
            it.vm = viewModel
        }
        initObserve()
    }

    private fun refresh() {
        val labelSource = intent.getParcelableExtra(Constants.LABEL) as? LabelViewData
            ?: throw IllegalStateException("required data of label")
        val labelEntity = LocalMainLabelMapper.toData(labelSource)
        binding.recyclerView.adapter = Adapter(labelEntity, viewModel)
        viewModel.fetchImages(labelEntity)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun initObserve() {
        with(viewModel) {
            state().observe(this@AlbumActivityByColor) {
                (binding.recyclerView.adapter as? Adapter)?.refresh(
                    it.images,
                    it.selectedImages,
                    it.isSelect
                )
            }
            finishActivity().observe(this@AlbumActivityByColor) {
                this@AlbumActivityByColor.finish()
            }
            goToDetail().observe(this@AlbumActivityByColor) {
                val intent = Intent(this@AlbumActivityByColor, DetailAlbumActivity::class.java)
                intent.putExtra(DETAIL_IMAGE, ImageModelMapper.fromData(it))
                startActivity(intent)
            }
            showDeleteConfirmMessage().observe(this@AlbumActivityByColor) {
                AlertDialog.Builder(this@AlbumActivityByColor, R.style.DialogTheme)
                    .setTitle("라벨링 해제")
                    .setMessage("정말 라벨링을 해제 하시겠습니?")
                    .setPositiveButton("확인") { _, _ ->
                        viewModel.requestDelete()
                    }.setNegativeButton("취소") { d, _ ->
                        d.dismiss()
                    }.show()
            }
        }
    }

    private class Adapter(
        private val label: LabelEntity,
        private val input: AlbumListInput
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val data = mutableListOf<ImageEntity>()
        private val selectedItems = hashSetOf<ImageEntity>()
        private var isSelected = false

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == ViewType.PLUS.ordinal)
                PlusViewHolder(
                    ItemLabelDetailPlusItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).apply {
                        this.input = this@Adapter.input
                    }
                )
            else ItemViewHolder(
                ItemLabelDetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    this.input = this@Adapter.input
                }
            )

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is PlusViewHolder -> holder.bind(label)
                is ItemViewHolder -> data[position - (if (isSelected) 0 else 1)].let { item ->
                    holder.bind(
                        item,
                        selectedItems.contains(item),
                        isSelected
                    )
                }
                else -> Unit
            }
        }

        fun refresh(data: List<ImageEntity>, selectedItems: Set<ImageEntity>, isSelected: Boolean) {
            this.data.clear()
            this.selectedItems.clear()
            this.data.addAll(data)
            this.selectedItems.addAll(selectedItems)
            this.isSelected = isSelected
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = data.size + (if (isSelected) 0 else 1)

        override fun getItemViewType(position: Int): Int = if (isSelected.not() && position == 0)
            ViewType.PLUS.ordinal
        else ViewType.ITEM.ordinal


        class ItemViewHolder(private val binding: ItemLabelDetailItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data: ImageEntity, selected: Boolean, canSelect: Boolean) {
                binding.data = data
                binding.selected = selected
                binding.canSelect = canSelect
                binding.executePendingBindings()
            }
        }

        class PlusViewHolder(private val binding: ItemLabelDetailPlusItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(label: LabelEntity) {
                binding.active = ColorUtil(label.color).getActive()
                binding.inactive = ColorUtil(label.color).getInactive()
                binding.executePendingBindings()
            }
        }


        enum class ViewType {
            PLUS, ITEM
        }
    }
}
