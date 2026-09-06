package com.flatcode.simplecomposeapps.multipledelete

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.multipledelete.ui.MultiDeleteScreen
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

class MultiDeleteActivity : AppCompatActivity() {

    private lateinit var viewModel: MultiDeleteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MultiDeleteViewModel::class.java]

        viewModel.setItems(DATA.MULTI_DELETE_VALUES)

        setContent {
            MultiDeleteScreen(
                viewModel = viewModel
            )
        }
    }
}