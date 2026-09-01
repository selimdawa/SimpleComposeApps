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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
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

    val errorColor = Color(0xFFF13A3A)
    val onBackgroundColor = Color(0xFF212121)

    Scaffold(
        topBar = {
            CalculatorTopAppBar(
                onBack = onBack,
                onClearHistory = { viewModel.clearHistory() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // History List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(history) { item ->
                    CalculatorHistoryItem(item = item)
                }
            }

            // Displays
            Text(
                text = expression,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.error,
                fontSize = 28.sp
            )

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

            // Keyboard
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorKey(
                        text = Strings.CLEAR,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(2f),
                        onClick = { viewModel.clearAll() }
                    )
                    CalculatorIconButton(
                        icon = AppIcons.Delete,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.deleteLast() }
                    )
                    CalculatorKey(
                        text = Strings.DIVIDE,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.appendValue(DATA.DIVIDE) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorKey(text = DATA.SEVEN, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.SEVEN) })
                    CalculatorKey(text = DATA.EIGHT, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.EIGHT) })
                    CalculatorKey(text = DATA.NINE, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.NINE) })
                    CalculatorKey(
                        text = Strings.MULTIPLY,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.appendValue(DATA.MULTIPLY) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorKey(text = DATA.FOUR, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.FOUR) })
                    CalculatorKey(text = DATA.FIVE, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.FIVE) })
                    CalculatorKey(text = DATA.SIX, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.SIX) })
                    CalculatorIconButton(
                        icon = AppIcons.Minus,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.appendValue(DATA.MINUS) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorKey(text = DATA.ONE, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.ONE) })
                    CalculatorKey(text = DATA.TWO, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.TWO) })
                    CalculatorKey(text = DATA.THREE, modifier = Modifier.weight(1f), onClick = { viewModel.appendValue(DATA.THREE) })
                    CalculatorIconButton(
                        icon = AppIcons.Add,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.appendValue(DATA.PLUS) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorKey(
                        text = DATA.ZERO,
                        modifier = Modifier.weight(2f),
                        onClick = { viewModel.appendValue(DATA.ZERO) }
                    )
                    CalculatorKey(
                        text = Strings.DOT,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.appendValue(DATA.DOT) }
                    )
                    CalculatorKey(
                        text = Strings.EQUALS,
                        color = errorColor,
                        contentColor = onBackgroundColor,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.evaluateExpression() }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorKey(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.track,
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
    color: Color = AppTheme.colors.track,
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