package com.nexters.fullstack.ui.widget.imagePicker

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.fullstack.R
import com.nexters.fullstack.domain.entity.ImageEntity
import com.nexters.fullstack.ui.decoration.SpaceBetweenRecyclerDecoration

class ImagePicker(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    private var imagePickerAdapter : ImagePickerAdapter = ImagePickerAdapter()
    private var images : ArrayList<ImageEntity> = ArrayList()
    private var selectedImages : ArrayList<ImageEntity> = ArrayList()
    private var colCount : Int = DEFAULT_COL_COUNT
    init{
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImagePicker,
            0, 0).apply {
            try {
                colCount = getInteger(R.styleable.ImagePicker_count, DEFAULT_COL_COUNT)
            } finally {
                recycle()
            }
        }
        initView()
    }

    fun initView(){
        layoutManager = GridLayoutManager(context, colCount)
        addItemDecoration(SpaceBetweenRecyclerDecoration(RV_MARGIN, RV_MARGIN))
        adapter = imagePickerAdapter
    }

    fun setImages(images : ArrayList<ImageEntity>){
        this.images.addAll(images)
        imagePickerAdapter.addItems(this.images as List<ImageEntity>)
    }

    fun getSelectedImages() = selectedImages

    fun picker(){
        clear()
        imagePickerAdapter.changeMode()
    }

    fun selectImage(position : Int){
        images[position].checked = !images[position].checked
        if(images[position].checked){
            selectedImages.add(images[position])
        } else {
            selectedImages.remove(images[position])
        }
        imagePickerAdapter.notifyItemChanged(position)
    }

    fun clear(){
        for (item : ImageEntity in images) item.checked = false
        selectedImages.clear()
    }

    fun getMode() = imagePickerAdapter.getMode()

    fun getImageAdapter() = imagePickerAdapter

    companion object{
        const val DEFAULT_COL_COUNT = 3
        const val RV_MARGIN = 3
    }
}