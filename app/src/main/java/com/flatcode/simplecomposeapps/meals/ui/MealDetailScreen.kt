package com.flatcode.simplecomposeapps.meals.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.meals.viewmodel.MealDetailViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun MealDetailScreen(
    id: String,
    thumb: String,
    onBack: () -> Unit,
    viewModel: MealDetailViewModel = hiltViewModel()
) {
    val meal by viewModel.observeMealDetailsLiveData().observeAsState()
    val isFavorite by viewModel.isMealFavorite(id).observeAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val view = LocalView.current

    LaunchedEffect(view) {
        val window = (view.context as? Activity)?.window ?: return@LaunchedEffect
        WindowCompat.getInsetsController(window, view).hide(WindowInsetsCompat.Type.statusBars())
    }
    LaunchedEffect(id) {
        viewModel.getMealDetail(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // AppBar replacement (Image)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                AsyncImage(
                    model = thumb,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Back Button Overlay
                Box(
                    modifier = Modifier
                        .padding(top = 40.dp, start = 10.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                        .align(Alignment.TopStart)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = AppIcons.Back,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                // Floating Action Button anchored to image bottom
                if (meal != null) {
                    FloatingActionButton(
                        onClick = {
                            if (isFavorite == null) {
                                viewModel.insertMeal(meal!!)
                                Toast.makeText(context, "Meal saved", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.deleteMeal(meal!!)
                                Toast.makeText(context, "Meal removed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.BottomEnd)
                            .offset(y = 28.dp),
                        shape = CircleShape,
                        containerColor = MC_TRACK,
                        contentColor = Color.White
                    ) {
                        Icon(
                            imageVector = if (isFavorite != null) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                }
            }

            // Progress bar
            if (meal == null) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MC_TRACK
                )
            }

            // Content below image
            if (meal != null) {
                val m = meal!!
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = AppIcons.Category,
                                contentDescription = null,
                                tint = MC_TRACK,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = m.strCategory ?: "",
                                color = MC_TRACK,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(2f)
                        ) {
                            Icon(
                                imageVector = AppIcons.Location,
                                contentDescription = null,
                                tint = MC_TRACK,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = m.strArea ?: "",
                                color = MC_TRACK,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Instructions",
                        color = COLOR_ERROR,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = m.strInstructions ?: "",
                        color = COLOR_ERROR,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // YouTube Icon
                    if (!m.strYoutube.isNullOrEmpty()) {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(m.strYoutube))
                                context.startActivity(intent)
                            }, modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Video,
                                contentDescription = "YouTube",
                                tint = MC_TRACK,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}