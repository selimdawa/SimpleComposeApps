package com.flatcode.simplecomposeapps.randomimagegenerating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImageInfoViewModel : ViewModel() {

    val catInfo: State<CatBreedInfo?>
        field = mutableStateOf<CatBreedInfo?>(null)

    fun setCatInfo(info: CatBreedInfo) {
        catInfo.value = info
    }
}
