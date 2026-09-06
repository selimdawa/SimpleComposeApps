package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun TodoDeleteDialog(
    title: String = Strings.DIALOG_DELETE_TITLE,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = COLOR_ON_BACKGROUND
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                ),
                color = COLOR_ON_BACKGROUND
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = Strings.DIALOG_BTN_YES,
                    color = COLOR_ON_BACKGROUND,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = Strings.DIALOG_BTN_NO,
                    color = COLOR_ON_BACKGROUND,
                    fontSize = 16.sp
                )
            }
        },
        containerColor = MC_BG,
        shape = MaterialTheme.shapes.medium
    )
}