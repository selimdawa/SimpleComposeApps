package com.flatcode.simplecomposeapps.newsapp.model

import com.flatcode.simplecomposeapps.utils.DATA
import java.io.Serializable

data class Source(
    var id: String = DATA.EMPTY,
    var name: String = DATA.EMPTY
) : Serializable