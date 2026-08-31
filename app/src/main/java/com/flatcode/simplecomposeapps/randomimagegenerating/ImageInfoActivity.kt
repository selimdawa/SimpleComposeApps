package com.flatcode.simplecomposeapps.randomimagegenerating

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.randomimagegenerating.ui.ImageInfoScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

class ImageInfoActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ImageInfoViewModel::class.java]

        val catInfo = CatBreedInfo(
            name = intent.getStringExtra(DATA.KEY_NAME) ?: DATA.UNKNOWN,
            origin = intent.getStringExtra(DATA.KEY_ORIGIN) ?: DATA.UNKNOWN,
            description = intent.getStringExtra(DATA.KEY_DESC) ?: DATA.UNKNOWN,
            temperament = intent.getStringExtra(DATA.KEY_TEMP) ?: DATA.UNKNOWN,
            wikiUrl = intent.getStringExtra(DATA.KEY_WIKI_URL) ?: DATA.EMPTY,
            moreLink = intent.getStringExtra(DATA.KEY_MORE_LINK) ?: DATA.EMPTY,
            imageUrl = intent.getStringExtra(DATA.KEY_IMAGE_URL) ?: DATA.EMPTY
        )
        viewModel.setCatInfo(catInfo)

        setContent {
            SimpleComposeAppsTheme {
                ImageInfoScreen(
                    viewModel = viewModel,
                    onBack = { finish() },
                    onOpenUrl = { url ->
                        if (url.isNotEmpty()) {
                            val browser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(browser)
                        }
                    }
                )
            }
        }
    }
}