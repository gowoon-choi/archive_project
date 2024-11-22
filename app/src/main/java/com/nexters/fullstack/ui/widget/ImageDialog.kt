package com.nexters.fullstack.ui.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.nexters.fullstack.databinding.DeleteDialogLayoutBinding

class ImageDialog : DialogFragment() {


    lateinit var binding: DeleteDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DeleteDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title
        binding.ivImage.setImageURI(Uri.parse(imageUrl))
        binding.tvNegative.text = negative
        binding.tvPositive.text = positive
        binding.tvPositive.setOnClickListener {
            setPositiveClickListener.invoke()
            dismiss()
        }
        binding.tvNegative.setOnClickListener { dismiss() }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog?.window?.setLayout(width, height)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    companion object {

        private lateinit var title: String
        private lateinit var positive: String
        private lateinit var setPositiveClickListener: () -> Unit
        private lateinit var negative: String
        private lateinit var imageUrl: String

        fun setTitle(value: String): Companion {
            this.title = value
            return this
        }

        fun setPositive(value: String): Companion {
            positive = value
            return this
        }

        fun setPositiveClickListener(value: () -> Unit): Companion {
            setPositiveClickListener = value
            return this
        }

        fun setNegative(value: String): Companion {
            negative = value
            return this
        }

        fun setImageUrl(value: String): Companion {
            imageUrl = value
            return this
        }

        fun build(): ImageDialog {
            return ImageDialog()
        }

    }

}