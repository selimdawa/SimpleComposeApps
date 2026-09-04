package com.flatcode.simplecomposeapps.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.calculator.CalculatorViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    onBack: () -> Unit
) {
    val expression by viewModel.expression.observeAsState("")
    val result by viewModel.result.observeAsState("")
    val history by viewModel.historyList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        // Section 1: History and Clear History Button
        Box(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 46.dp)
            ) {
                items(history) { item ->
                    CalculatorHistoryItem(item = item)
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(COLOR_ERROR)
                    .clickable { viewModel.clearHistory() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.ClearAll,
                    contentDescription = null,
                    tint = COLOR_ON_BACKGROUND,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // Section 2: txt_place_holder
        Text(
            text = expression,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.End,
            color = COLOR_ERROR,
            fontSize = 28.sp
        )

        // Section 3: txt_result
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        // Section 4: Keyboard
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorKey(
                    text = Strings.CLEAR,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(2f),
                    fontSize = 24,
                    onClick = { viewModel.clearAll() }
                )
                CalculatorIconButton(
                    icon = AppIcons.DeleteCal,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.deleteLast() }
                )
                CalculatorKey(
                    text = Strings.DIVIDE,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    fontSize = 28,
                    onClick = { viewModel.appendValue(DATA.DIVIDE) }
                )
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorKey(text = DATA.SEVEN, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.SEVEN) })
                CalculatorKey(text = DATA.EIGHT, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.EIGHT) })
                CalculatorKey(text = DATA.NINE, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.NINE) })
                CalculatorKey(
                    text = Strings.MULTIPLY,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    fontSize = 28,
                    onClick = { viewModel.appendValue(DATA.MULTIPLY) }
                )
            }

            // Row 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorKey(text = DATA.FOUR, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.FOUR) })
                CalculatorKey(text = DATA.FIVE, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.FIVE) })
                CalculatorKey(text = DATA.SIX, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.SIX) })
                CalculatorIconButton(
                    icon = AppIcons.Minus,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.appendValue(DATA.MINUS) }
                )
            }

            // Row 4
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorKey(text = DATA.ONE, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.ONE) })
                CalculatorKey(text = DATA.TWO, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.TWO) })
                CalculatorKey(text = DATA.THREE, color = MC_TRACK, modifier = Modifier.weight(1f), fontSize = 26, onClick = { viewModel.appendValue(DATA.THREE) })
                CalculatorIconButton(
                    icon = AppIcons.Add,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.appendValue(DATA.PLUS) }
                )
            }

            // Row 5
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorKey(
                    text = DATA.ZERO,
                    color = MC_TRACK,
                    modifier = Modifier.weight(2f),
                    fontSize = 36,
                    onClick = { viewModel.appendValue(DATA.ZERO) }
                )
                CalculatorKey(
                    text = Strings.DOT,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    fontSize = 36,
                    onClick = { viewModel.appendValue(DATA.DOT) }
                )
                CalculatorKey(
                    text = Strings.EQUALS,
                    color = COLOR_ERROR,
                    contentColor = COLOR_ON_BACKGROUND,
                    modifier = Modifier.weight(1f),
                    fontSize = 36,
                    onClick = { viewModel.evaluateExpression() }
                )
            }
        }
    }
}

@Composable
fun CalculatorKey(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    contentColor: Color = Color.White,
    fontSize: Int = 26,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CalculatorIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    color: Color,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(28.dp)
        )
    }
}
