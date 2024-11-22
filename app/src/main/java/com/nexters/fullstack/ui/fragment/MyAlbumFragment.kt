package com.nexters.fullstack.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.nexters.feature.util.toLabelaryColor
import com.nexters.fullstack.BR
import com.nexters.fullstack.R
import com.nexters.fullstack.base.BaseAdapter
import com.nexters.fullstack.base.BaseFragment
import com.nexters.fullstack.data.mapper.LabelModelMapper
import com.nexters.fullstack.databinding.FragmentMyalbumBinding
import com.nexters.fullstack.databinding.ItemAlbumWithLabelBinding
import com.nexters.fullstack.databinding.ItemLabelInAlbumBinding
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.mapper.LabelSourceMapper
import com.nexters.fullstack.presentaion.mapper.LocalMainLabelMapper
import com.nexters.fullstack.presentaion.model.LabelingImage
import com.nexters.fullstack.presentaion.viewmodel.MyAlbumOutput
import com.nexters.fullstack.presentaion.viewmodel.MyAlbumViewModel
import com.nexters.fullstack.ui.activity.AlbumActivityByColor
import com.nexters.fullstack.ui.activity.CreateLabelActivity
import com.nexters.fullstack.ui.adapter.LocalImageAdapter
import com.nexters.fullstack.ui.adapter.listener.ItemClickListener
import com.nexters.fullstack.ui.adapter.listener.OnClickItemDelegate
import com.nexters.fullstack.ui.holder.LocalImageViewHolder
import com.nexters.fullstack.ui.widget.bottomsheet.LabelManagerBottomSheetDialog
import com.nexters.fullstack.ui.widget.bottomsheet.recyclerview.GridLayoutRecyclerOnScrollListener
import com.nexters.fullstack.util.Constants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyAlbumFragment : BaseFragment<FragmentMyalbumBinding, MyAlbumViewModel>() {

    override val layoutRes: Int = R.layout.fragment_myalbum

    override val viewModel: MyAlbumViewModel by sharedViewModel()

    private val adapter by lazy { Adapter(::onClickItem, ::onClickMenu) }
    private val offsetRecyclerListener by lazy {
        GridLayoutRecyclerOnScrollListener(binding.toolbar, binding.addLabel, binding.title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        binding.input = viewModel
        binding.output = viewModel

        with(viewModel as MyAlbumOutput) {
            state().observe(viewLifecycleOwner) {
                adapter.refresh(it.labels.map {
                    Adapter.ViewHolderData(
                        it.first,
                        it.second,
                        it.third
                    )
                })
            }
            showErrorToast().observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
            showDeleteSuccessToast().observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Success Delete", Toast.LENGTH_LONG).show()
            }
            goToLabelDetail().observe(viewLifecycleOwner) {

            }
            goToLabelEdit().observe(viewLifecycleOwner) {
                requireContext().startActivity(
                    Intent(
                        this@MyAlbumFragment.requireContext(),
                        CreateLabelActivity::class.java
                    )
                )
            }
        }
    }

    private fun initView() {
        binding.rvUserImage.addOnScrollListener(offsetRecyclerListener)
        binding.rvUserImage.adapter = adapter
        binding.addLabel.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    this@MyAlbumFragment.requireContext(),
                    CreateLabelActivity::class.java
                )
            )
        }
    }

    companion object {
        fun getInstance(): MyAlbumFragment {
            val fragment = MyAlbumFragment().apply { bundleOf("tag" to "myAlbumFragment") }
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onDestroy() {
        binding.rvUserImage.removeOnScrollListener(offsetRecyclerListener)
        super.onDestroy()
    }

    private fun onClickItem(item: LabelEntity) {
        val intent = Intent(context, AlbumActivityByColor::class.java)
        val labelMapper = LocalMainLabelMapper.fromData(item)
        intent.putExtra(Constants.LABEL, labelMapper)
        startActivity(intent)
    }

    private fun onClickMenu(item: LabelEntity) {
        LabelManagerBottomSheetDialog.getInstance(item).apply {
            onClickPositive = {
                viewModel.deleteLabel(item)
            }
        }.show(requireActivity().supportFragmentManager, this.tag)
    }


    class Adapter(
        private val onClickItem: (LabelEntity) -> Unit,
        private val onClickMenu: (LabelEntity) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val data = mutableListOf<ViewHolderData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = ItemLabelInAlbumBinding.inflate(LayoutInflater.from(parent.context))
            return ViewHolder(binding).apply {
                binding.album.setOnClickListener { onClickItem.invoke(data[adapterPosition].label) }
                binding.infoMore.setOnClickListener { onClickMenu.invoke(data[adapterPosition].label) }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as? ViewHolder)?.bind(data[position])
        }

        override fun getItemCount(): Int = data.size

        fun refresh(data: List<ViewHolderData>) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }


        class ViewHolder(
            private val binding: ItemLabelInAlbumBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(data: ViewHolderData) {
                binding.data = data
                binding.executePendingBindings()
            }
        }


        data class ViewHolderData(
            val label: LabelEntity,
            val image: ImageEntity?,
            val size: Int
        ) {
            val sizeString = size.toString()
            val imageUrl = image?.image?.originUrl
        }
    }
}
