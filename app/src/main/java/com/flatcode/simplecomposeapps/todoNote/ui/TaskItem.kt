package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK

@Composable
fun TaskItem(
    task: Task, modifier: Modifier = Modifier, onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(COLOR_ON_BACKGROUND),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.size(48.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = COLOR_ERROR,
                uncheckedColor = COLOR_ERROR,
                checkmarkColor = COLOR_ON_BACKGROUND
            )
        )

        Text(
            text = task.name,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 3.dp),
            fontSize = 20.sp,
            color = COLOR_ERROR,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (task.completed) TextDecoration.LineThrough else null
        )

        if (task.important) {
            Icon(
                imageVector = AppIcons.PriorityHigh,
                contentDescription = "Important",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp),
                tint = MC_TRACK
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(
            name = "Example Task", important = true, completed = false
        )
    )
}