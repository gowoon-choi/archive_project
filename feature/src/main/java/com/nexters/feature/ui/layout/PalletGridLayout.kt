package com.nexters.feature.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.nexters.feature.R
import com.nexters.feature.databinding.ItemPalletViewBinding
import com.nexters.feature.ui.data.pallet.PalletItem
import com.nexters.feature.util.ColorUtils
import com.nexters.feature.viewmodel.PalletViewModel

class PalletGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defaultAttr: Int = 0
) : GridLayout(context, attrs, defaultAttr), LifecycleObserver, LifecycleOwner,
    ViewModelStoreOwner {

    private val _items = mutableListOf<PalletItem>()

    lateinit var setOnLabelClickListener: (PalletItem) -> Unit

    private val lifecycleRegister = LifecycleRegistry(this)

    private val viewModelStore = ViewModelStore()

    val viewModel = ViewModelProvider(this).get(PalletViewModel::class.java)

    lateinit var selectedView: View

    lateinit var selectedPalletItem: PalletItem

    init {
        columnCount = 2
        _items.addAll(viewModel._colors)
    }

    fun setOnInitView(data: PalletItem? = null) {
        _items.forEachIndexed { index, item ->
            val childView = DataBindingUtil.bind<ItemPalletViewBinding>(
                LayoutInflater.from(context).inflate(R.layout.item_pallet_view, this, false)
            )

            childView?.lifecycleOwner = this
            childView?.executePendingBindings()

            val textView = childView?.label

            if (textView != null) {
                if (index % 2 == 0) {
                    textView.width =
                        (resources.displayMetrics.widthPixels / 2) - textView.marginRight - (textView.marginLeft * 2)
                } else {
                    textView.width =
                        (resources.displayMetrics.widthPixels / 2) - textView.marginRight - textView.marginLeft
                }
            }


            textView?.run {
                text = item.name

                setBackgroundColor(ColorUtils(item.name, context).getInactive())
                setTextColor(ColorUtils(item.name, context).getText())
                setOnClickListener { itemView ->
                    if (::selectedView.isInitialized) {
                        selectedView.isSelected = false
                        selectedView.setBackgroundColor(
                            ColorUtils(
                                selectedPalletItem.name,
                                context
                            ).getInactive()
                        )
                    }

                    selectedPalletItem = item
                    selectedView = itemView


                    itemView.isSelected = true

                    setBackgroundColor(ColorUtils(item.name, context).getActive())
                    if (::setOnLabelClickListener.isInitialized) {
                        setOnLabelClickListener(item)
                    }

                    invalidate()
                }
            }

            if (data?.name == item.name) {
                textView?.performClick()
            }

            addView(childView?.root)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onStartEvent(event: Lifecycle.Event) {
        lifecycleRegister.handleLifecycleEvent(event)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegister
    }

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }
}