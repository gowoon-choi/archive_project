package com.nexters.feature.ui.data.pallet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PalletItem(
    val name: String
) : Parcelable