package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.flatcode.simplecomposeapps.blogger.model.Comment
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import java.text.SimpleDateFormat
import java.util.Locale

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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = comment.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = comment.name ?: DATA.EMPTY,
                        color = COLOR_ERROR,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formattedDate,
                        color = COLOR_ERROR.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = comment.comment ?: DATA.EMPTY,
                    color = COLOR_ERROR,
                    fontSize = 14.sp
                )
            }
        }
    }
}