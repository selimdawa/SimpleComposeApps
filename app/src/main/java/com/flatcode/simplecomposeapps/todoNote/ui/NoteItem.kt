package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun NoteItem(
    note: Notes,
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_TRACK)
                .padding(10.dp)
        ) {
            Text(
                text = note.title,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = COLOR_ON_BACKGROUND
            )

            Text(
                text = note.content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                color = COLOR_ON_BACKGROUND
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = COLOR_ON_BACKGROUND
                )

                Text(
                    text = note.dateCreatedFormatted,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f),
                    fontSize = 12.sp,
                    color = COLOR_ON_BACKGROUND
                )

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Notes(
            title = "Sample Note Title",
            content = "This is a sample content for the note that can be up to five lines long before it gets elipsized.",
            date = System.currentTimeMillis()
        )
    )
}
