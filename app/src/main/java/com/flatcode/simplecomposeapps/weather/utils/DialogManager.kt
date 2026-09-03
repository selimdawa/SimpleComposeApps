package com.flatcode.simplecomposeapps.weather.utils

import android.app.AlertDialog
import android.content.Context
import androidx.compose.ui.platform.ComposeView
import com.flatcode.simplecomposeapps.weather.ui.SearchDialogContent

object DialogManager {
    fun locationSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable Location")
        dialog.setMessage("Location disabled, do you want enable location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener) {
        val composeView = ComposeView(context).apply {
            setContent {
                SearchDialogContent(
                    onConfirm = { name ->
                        listener.onClick(name)
                        // We need to dismiss the AlertDialog. How?
                        // We'll store it below.
                    },
                    onDismiss = {
                        // We'll store it below.
                    }
                )
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setView(composeView)
            .create()

        // Update the callbacks to dismiss the dialog
        composeView.setContent {
            SearchDialogContent(
                onConfirm = { name ->
                    listener.onClick(name)
                    dialog.dismiss()
                },
                onDismiss = {
                    dialog.dismiss()
                }
            )
        }

        dialog.show()
    }

    interface Listener {
        fun onClick(name: String?)
    }
}