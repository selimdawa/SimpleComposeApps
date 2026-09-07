package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun TaskItem(
    task: Task, modifier: Modifier = Modifier, onCheckedChange: (Boolean) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(COLOR_ON_BACKGROUND)
    ) {
        val (checkBox, text, priority) = createRefs()

        Checkbox(
            checked = task.completed,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .constrainAs(checkBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(48.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = COLOR_ERROR,
                uncheckedColor = COLOR_ERROR,
                checkmarkColor = COLOR_ON_BACKGROUND
            ))

        Text(
            text = task.name,
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(checkBox.end)
                    top.linkTo(checkBox.top)
                    bottom.linkTo(checkBox.bottom)
                    end.linkTo(priority.start, margin = 3.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 3.dp),
            fontSize = 20.sp,
            color = if (task.important) COLOR_ERROR else COLOR_ON_BACKGROUND,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (task.completed) TextDecoration.LineThrough else null)

        if (task.important) {
            Icon(
                imageVector = AppIcons.PriorityHigh,
                contentDescription = "Important",
                modifier = Modifier
                    .constrainAs(priority) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 5.dp)
                    }
                    .size(24.dp),
                tint = MC_TRACK)
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