package com.nexters.feature.viewmodel

import androidx.lifecycle.ViewModel
import com.nexters.feature.ui.data.pallet.PalletItem

class PalletViewModel : ViewModel() {
    internal val _colors =
        listOf(
            PalletItem("Yellow"),
            PalletItem("Orange"),
            PalletItem("Red"),
            PalletItem("Pink"),
            PalletItem("Violet"),
            PalletItem("Purple Blue"),
            PalletItem("Blue"),
            PalletItem("Peacock Green"),
            PalletItem("Green"),
            PalletItem("Gray")
        )

}