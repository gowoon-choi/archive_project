package com.nexters.fullstack.ui.widget.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexters.fullstack.BR
import com.nexters.fullstack.util.Constants
import com.nexters.fullstack.R
import com.nexters.fullstack.databinding.LayoutLabelManagerBottomSheetBinding
import com.nexters.fullstack.data.db.entity.LabelModel
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.presentaion.model.dialog.DeleteDialogItem
import com.nexters.fullstack.ui.activity.CreateLabelActivity
import com.nexters.fullstack.ui.adapter.listener.BottomSheetClickListener
import com.nexters.fullstack.ui.adapter.source.ItemType
import com.nexters.fullstack.presentaion.viewmodel.BottomSheetViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class LabelManagerBottomSheetDialog() : BottomSheetDialogFragment(), KoinComponent, BottomSheetClickListener {

    private lateinit var binding: LayoutLabelManagerBottomSheetBinding

    private val bottomSheetViewModel by viewModel<BottomSheetViewModel>()

    private val dialog by lazy {
        AlertDialog.Builder(this.requireContext(), R.style.DialogTheme)
    }

    override fun getTheme(): Int {
        return R.style.iOSBottomSheetDialogTheme
    }

    var onClickPositive : () -> Unit = {}
    var onClickNegative : () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_label_manager_bottom_sheet,
            container,
            false
        )
        binding.setVariable(BR.vm, bottomSheetViewModel)
        binding.event = this@LabelManagerBottomSheetDialog
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBottomSheet.setHasFixedSize(true)
        setObserver()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    private fun setObserver() {
        with(bottomSheetViewModel.output) {
            getDialogItem().observe(this@LabelManagerBottomSheetDialog.viewLifecycleOwner) {
                dialog
                    .setTitle(it.title)
                    .setMessage(it.message)
                    .setNegativeButton(it.cancel) { _, _ ->
                        onClickNegative.invoke()
                    }.setPositiveButton(it.positive) { _, _ ->
                        onClickPositive.invoke()
                    }.create()
                    .show()
            }
        }
    }

    /**
     * BottomSheet Click Event
     **/
    override fun onClick(type: ItemType) {
        when (type) {
            ItemType.DELETE -> {
                bottomSheetViewModel.input.setDialogItem(
                    DeleteDialogItem(
                        requireContext().getString(R.string.title),
                        requireContext().getString(R.string.message),
                        requireContext().getString(R.string.cancel),
                        requireContext().getString(R.string.delete)
                    )
                )
                dismiss()
            }
            ItemType.UPDATE -> {
                dismiss()
                val intent = Intent(requireContext(), CreateLabelActivity::class.java)
                intent.putExtra(Constants.LABEL_MODIFY_KEY, arguments?.getParcelable(Constants.BOTTOM_SHEET_KEY) as? LabelEntity)
                requireContext().startActivity(intent)
            }
        }
    }


    companion object {
        fun getInstance(
            data: LabelEntity
        ): LabelManagerBottomSheetDialog {
            return LabelManagerBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.BOTTOM_SHEET_KEY, data)
                }
            }
        }
    }
}