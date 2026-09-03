package com.flatcode.simplecomposeapps.calculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.calculator.data.CalculatorEntity
import com.flatcode.simplecomposeapps.ui.theme.Gray2
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun CalculatorHistoryItem(
    item: CalculatorEntity,
    modifier: Modifier = Modifier
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcTrack = rememberAttributeColor("mc_track", Color.White, themeId)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = item.expression,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            color = Gray2,
            fontSize = 16.sp
        )
        Text(
            text = item.result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            color = mcTrack,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorHistoryItemPreview() {
    SimpleComposeAppsTheme {
        CalculatorHistoryItem(
            item = CalculatorEntity(
                expression = "10 + 20",
                result = "30"
            )
        )
    }
}
