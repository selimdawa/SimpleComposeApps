package com.flatcode.simplecomposeapps.multipledelete

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.multipledelete.ui.MultiDeleteScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import io.selimdawa.multicolors.MultiColorManager

class MultiDeleteActivity : AppCompatActivity() {

    private lateinit var viewModel: MultiDeleteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MultiDeleteViewModel::class.java]

        val initialItems = resources.getStringArray(R.array.values).toList()
        viewModel.setItems(initialItems)

        setContent {
            SimpleComposeAppsTheme {
                MultiDeleteScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}