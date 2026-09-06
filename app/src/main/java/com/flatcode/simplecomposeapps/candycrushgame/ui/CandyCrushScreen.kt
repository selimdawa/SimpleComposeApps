package com.flatcode.simplecomposeapps.candycrushgame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.candycrushgame.CandyCrushViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun CandyCrushScreen(
    viewModel: CandyCrushViewModel
) {
    val score by viewModel.score
    val board = viewModel.board

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(AppIcons.CandyBackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent, topBar = {
                ToolbarContent(
                    title = Strings.CANDY_CRUSH_GAME, hasBack = false
                )
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScoreCard(score)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(viewModel.noOfBlocks),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        userScrollEnabled = false
                    ) {
                        itemsIndexed(board) { index, candyResId ->
                            CandyItem(
                                candyResId = candyResId, onSwipe = { direction ->
                                    val targetIndex = when (direction) {
                                        SwipeDirection.LEFT -> index - 1
                                        SwipeDirection.RIGHT -> index + 1
                                        SwipeDirection.TOP -> index - viewModel.noOfBlocks
                                        SwipeDirection.BOTTOM -> index + viewModel.noOfBlocks
                                    }
                                    viewModel.swapCandies(index, targetIndex)
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreCard(score: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x9900ddff)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = Strings.SCORE_LABEL,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = score.toString(),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
