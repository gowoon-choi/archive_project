package com.nexters.fullstack.presentaion.model

import android.os.Parcelable
import com.nexters.feature.ui.data.pallet.PalletItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainMakeLabelSource(val labelText: String, val palletItem: PalletItem) :
    Parcelable