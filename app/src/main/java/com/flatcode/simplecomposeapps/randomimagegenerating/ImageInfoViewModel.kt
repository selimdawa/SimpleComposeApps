package com.flatcode.simplecomposeapps.randomimagegenerating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImageInfoViewModel : ViewModel() {

    private val _catInfo = mutableStateOf<CatBreedInfo?>(null)
    val catInfo: State<CatBreedInfo?> = _catInfo

    fun setCatInfo(info: CatBreedInfo) {
        _catInfo.value = info
    }
}
