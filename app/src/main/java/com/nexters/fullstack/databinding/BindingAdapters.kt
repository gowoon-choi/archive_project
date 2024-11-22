package com.nexters.fullstack.databinding

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.nexters.feature.util.*
import com.nexters.fullstack.R
import com.nexters.fullstack.databinding.BindingAdapters.setLocalImageThumbnail
import com.nexters.fullstack.domain.entity.LabelEntity
import com.nexters.fullstack.domain.entity.FileImageEntity
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.presentaion.model.*
import com.nexters.fullstack.presentaion.model.bottomsheet.BottomSheetItem
import com.nexters.fullstack.presentaion.viewmodel.MainViewModel
import com.nexters.fullstack.ui.adapter.*
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration
import com.nexters.fullstack.util.ColorUtil
import com.nexters.fullstack.util.extension.dpToPx
import com.yuyakaido.android.cardstackview.CardStackView
import org.joda.time.DateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:localImage")
    fun ImageView.setLocalImageThumbnail(url: String?) {
        url?.takeIf { it.isNotEmpty() }?.let {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions().centerCrop())
                .into(this)
        } ?: run {
            setBackgroundColor(ContextCompat.getColor(context, R.color.depth3))
            setImageResource(R.drawable.ic_ico_empty_screenshot)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }


    @JvmStatic
    @BindingAdapter("app:labelAlbumImage")
    fun ImageView.setLabelAlbumImage(url: String?) {
        url?.let {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions().centerCrop())
                .into(this)
        } ?: run {
            Glide.with(this)
                .load(R.drawable.ic_ico_empty_screenshot)
                .into(this)
        }
    }

    @JvmStatic
    @BindingConversion
    fun convertBooleanToVisibility(value: Boolean): Int {
        return if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @BindingAdapter("app:textUtil")
    fun TextView.setTextDateFormat(value: Long?) {
        if(value != null) {
            val dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())

            text = dateFormat.format(Date(value))
        }
    }
}

@BindingAdapter("app:setStackItems")
fun RecyclerView.setStackBinding(items: List<FileImageViewData>?) {
    val stackAdapter = adapter as? MainStackAdapter

    items?.let {
        stackAdapter?.addItems(it)
    }

    stackAdapter?.notifyDataSetChanged()
}

@BindingAdapter("app:localLabels")
fun RecyclerView.setLocalLabel(items: List<LabelViewData>?) {
    val localAdapter = adapter as? MyLabelAdapter

    items?.let {
        localAdapter?.addItems(it)
    }

    localAdapter?.notifyDataSetChanged()
}

@BindingAdapter("app:bottomSheetItem", "app:onClickEvent")
fun RecyclerView.setBottomSheetItem(items: List<BottomSheetItem>?, onClickEvent: Any?) {
    adapter?.run {
        if (this is BottomSheetAdapter) {
            items?.let {
                addItems(it)
                notifyDataSetChanged()
            }
        }
    } ?: BottomSheetAdapter(onClickEvent).also {
        adapter = it
    }
}

@BindingAdapter(requireAll = false, value = ["app:labelAlbumItems", "app:onClickLabelItemEvent"])
fun RecyclerView.setLabelAlbumItems(items: List<ImageEntity>?, event: LabelAlbumDelegate?) {
    adapter?.let { labelAdapter ->
        Log.e("adapter", "call")

        if (labelAdapter is LabelAlbumRecyclerAdapter) {
            items?.let {
                labelAdapter.clearItems()
                labelAdapter.addItems(it)
                labelAdapter.notifyDataSetChanged()
            } ?: run {
                labelAdapter.clearItems()
            }
        }
    } ?: run {
        Log.e("run", "call")
        event?.let {
            Log.e("event", "call")
            val labelAlbumAdapter = LabelAlbumRecyclerAdapter(it::onClick)

            addItemDecoration(SpaceBetweenRecyclerDecoration(vertical = 16, horizontal = 14))
            adapter = labelAlbumAdapter
        }
    }
}

@BindingAdapter(
    "app:onApproveButtonClickListener",
    requireAll = true
)
fun ImageView.setOnApproveButtonClickListener(
    emit: MainViewModel.MainInput?
) {
    setOnClickListener {
        emit?.clickButton(MainLabelState.Approve)
    }
}

@BindingAdapter("app:onRejectButtonClickListener")
fun ImageView.setOnRejectButtonClickListener(emit: MainViewModel.MainInput?) {
    setOnClickListener {
        emit?.clickButton(MainLabelState.Reject)
    }
}

@BindingAdapter("labelaryLabel")
fun TextView.setLabel(label: LabelEntity?) {
    label?.let {
        text = it.text
        it.color?.toLabelaryColor()?.let { color ->
            background = GradientDrawable().apply {
                cornerRadius = 4.dpToPx(context)
                setColor(color.getInactive(context))
            }
            setTextColor(color.getText(context))
        }
    } ?: let {
        text = ""
        background = null
    }
}

@BindingAdapter("text")
fun TextView.setTextResource(text: String) {
    setText(text)
}

@BindingAdapter("setLabelActiveColor")
fun TextView.setLabelActiveColor(color : String?){
    color?.let {
        setTextColor(ColorUtil(it).getActive())
    }
}

@BindingAdapter("setLabelTabColor")
fun TextView.setLabelTabColor(color : String?){
    color?.let {
        val labelColor = ColorUtil(color)
        setTextColor(labelColor.getText())
        Glide.with(context)
            .load(ColorDrawable(labelColor.getInactive()))
            .apply(RequestOptions().transform(RoundedCorners(2.dpToPx(context).toInt())))
            .into(object : CustomViewTarget<TextView,Drawable>(this){
                override fun onLoadFailed(errorDrawable: Drawable?) = Unit
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    this@setLabelTabColor.background = resource
                }

                override fun onResourceCleared(placeholder: Drawable?) = Unit
            })
    } ?: let{
        setTextColor(ContextCompat.getColor(context,R.color.inactive))
        background = null
    }
}

@BindingAdapter("enabled")
fun View.enabled(enabled : Boolean?) {
    isEnabled = enabled ?: false
}


interface LabelAlbumDelegate {
    fun onClick(item: ImageEntity)
}
