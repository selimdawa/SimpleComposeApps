package com.flatcode.simplecomposeapps.randomcatsimage.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import com.flatcode.simplecomposeapps.randomcatsimage.RandomCatsImageViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.flatcode.simplecomposeapps.utils.SimpleBlurTransformation

@Composable
fun RandomCatsImageContent(
    viewModel: RandomCatsImageViewModel, onDownload: (String) -> Unit
) {
    val imageUrl by viewModel.imageUrl.observeAsState("")
    var isFullScreen by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }

    var containerWidth by remember { mutableFloatStateOf(0f) }
    var containerHeight by remember { mutableFloatStateOf(0f) }
    var imageRatio by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(imageUrl) {
        offsetX = 0f
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .onSizeChanged {
                        containerWidth = it.width.toFloat()
                        containerHeight = it.height.toFloat()
                    }) {
                val context = LocalContext.current
                val imageModel = imageUrl.ifEmpty { image_profile }

                AsyncImage(
                    model = ImageRequest.Builder(context).data(imageModel).crossfade(true)
                        .transformations(SimpleBlurTransformation(50f)).build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    model = imageModel,
                    contentDescription = "Cat Image",
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Success) {
                            val size = state.painter.intrinsicSize
                            if (size.height > 0) {
                                imageRatio = size.width / size.height
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (isFullScreen) {
                            Modifier.wrapContentWidth(unbounded = true).graphicsLayer {
                                    translationX = offsetX
                                }.pointerInput(imageRatio, containerWidth, containerHeight) {
                                    val scaledWidth = containerHeight * imageRatio
                                    val maxOffset = if (scaledWidth > containerWidth) {
                                        (scaledWidth - containerWidth) / 2
                                    } else 0f

                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        offsetX = (offsetX + dragAmount.x).coerceIn(
                                            -maxOffset, maxOffset
                                        )
                                    }
                                }
                        } else Modifier),
                    contentScale = if (isFullScreen) ContentScale.FillHeight else ContentScale.Fit)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                onClick = { if (imageUrl.isNotEmpty()) onDownload(imageUrl) },
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MC_BG),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Down),
                        contentDescription = "Download",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Card(
                onClick = {
                    isFullScreen = !isFullScreen
                    offsetX = 0f
                },
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MC_BG),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFullScreen) Icons.Default.FullscreenExit else AppIcons.Fullscreen,
                        contentDescription = "Toggle Full Screen",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Card(
                onClick = { viewModel.getImage() },
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MC_BG),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Refresh),
                        contentDescription = "Refresh",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}