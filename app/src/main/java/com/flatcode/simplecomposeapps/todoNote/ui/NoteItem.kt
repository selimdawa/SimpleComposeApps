package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.asapCondensed

@Composable
fun NoteItem(
    note: Notes, modifier: Modifier = Modifier, onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_BG)
                .padding(10.dp)
        ) {
            Text(
                text = note.title,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = COLOR_ON_BACKGROUND,
                fontFamily = asapCondensed
            )

            Text(
                text = note.content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                fontSize = 14.sp,
                minLines = 5,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                color = COLOR_ON_BACKGROUND,
                fontFamily = asapCondensed
            )

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = AppIcons.EventNote,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = COLOR_ON_BACKGROUND
                )

                Text(
                    text = note.dateCreatedFormatted,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),
                    fontSize = 12.sp,
                    color = COLOR_ON_BACKGROUND,
                    fontFamily = asapCondensed
                )

                IconButton(
                    onClick = onDeleteClick, modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.DeleteCal,
                        contentDescription = "Delete",
                        tint = COLOR_ON_BACKGROUND,
                        modifier = Modifier.size(24.dp)
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