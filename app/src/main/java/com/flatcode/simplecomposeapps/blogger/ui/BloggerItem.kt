package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.flatcode.simplecomposeapps.blogger.model.Post
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.DATA.MC_BG
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BloggerItem(post: Post, onClick: () -> Unit) {
    val inputDateFormat = remember { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH) }
    val outputDateFormat = remember { SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH) }

    val document = remember(post.content) { Jsoup.parse(post.content ?: DATA.EMPTY) }
    val imageUrl = remember(document) {
        try {
            document.select("img").attr("src")
        } catch (_: Exception) {
            ""
        }
    }
    val description = remember(document) { document.text() }
    val formattedDate = remember(post.published) {
        try {
            val date = inputDateFormat.parse(post.published ?: DATA.EMPTY)
            if (date != null) outputDateFormat.format(date) else post.published ?: DATA.EMPTY
        } catch (_: Exception) {
            post.published ?: DATA.EMPTY
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_BG)
        ) {
            Column {
                Text(
                    text = post.title ?: DATA.EMPTY,
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = Strings.publishInfo(post.authorName ?: DATA.EMPTY, formattedDate),
                    color = White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier.size(80.dp), shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 10.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        ), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
                    ) {
                        if (imageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Text(
                        text = description,
                        color = White,
                        fontSize = 14.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}