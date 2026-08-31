package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BloggerCommentItem(comment: Comment) {
    val inputDateFormat = remember { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH) }
    val outputDateFormat = remember { SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH) }

    val formattedDate = remember(comment.published) {
        try {
            val date = inputDateFormat.parse(comment.published ?: DATA.EMPTY)
            if (date != null) outputDateFormat.format(date) else comment.published ?: DATA.EMPTY
        } catch (_: Exception) {
            comment.published ?: DATA.EMPTY
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = comment.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = null // Placeholder could be AppIcons.Person but AsyncImage wants painter
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = comment.name ?: DATA.EMPTY,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formattedDate,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = comment.comment ?: DATA.EMPTY,
            color = Color.White,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
    }
}