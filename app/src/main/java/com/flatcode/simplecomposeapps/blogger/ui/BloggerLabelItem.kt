package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.White

@Composable
fun BloggerLabelItem(label: Label) {
    Surface(
        color = MC_TRACK, shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = label.label ?: "",
            color = White,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}