package com.flatcode.simplecomposeapps.randomimagegenerating

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.randomimagegenerating.ui.RandomImageGeneratingScreen
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.launchActivity
import io.selimdawa.multicolors.MultiColorManager

class RandomImageGeneratingActivity : AppCompatActivity() {

    private lateinit var viewModel: RandomImageGeneratingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[RandomImageGeneratingViewModel::class.java]

        setContent {
            RandomImageGeneratingScreen(
                viewModel = viewModel,
                onNavigateToInfo = {
                    val catInfo = viewModel.catBreedInfo.value
                    if (catInfo != null) {
                        launchActivity<ImageInfoActivity> {
                            putExtra(DATA.KEY_NAME, catInfo.name)
                            putExtra(DATA.KEY_ORIGIN, catInfo.origin)
                            putExtra(DATA.KEY_DESC, catInfo.description)
                            putExtra(DATA.KEY_TEMP, catInfo.temperament)
                            putExtra(DATA.KEY_WIKI_URL, catInfo.wikiUrl)
                            putExtra(DATA.KEY_MORE_LINK, catInfo.moreLink)
                            putExtra(DATA.KEY_IMAGE_URL, catInfo.imageUrl)
                        }
                    }
                },
                onDownload = { url ->
                    val browser = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(browser)
                },
            )
        }
    }
}
