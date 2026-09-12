package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.blogger.model.Label
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR

@Composable
fun BloggerLabelItem(label: Label) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .defaultMinSize(minWidth = 80.dp)
            .background(Color(0xFFEFEFFE), shape = RoundedCornerShape(15.dp))
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(15.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label.label ?: "",
            color = COLOR_ERROR,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}