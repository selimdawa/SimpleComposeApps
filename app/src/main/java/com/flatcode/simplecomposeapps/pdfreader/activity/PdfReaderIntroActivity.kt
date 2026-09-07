package com.flatcode.simplecomposeapps.pdfreader.activity

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PdfReaderIntroActivity : AppIntro() {

    private val backgroundColor = Color.parseColor("#000000")

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val sliderPage = SliderPage().apply {
            title = Strings.TITLE_PERMISSION
            description = Strings.DESCRIPTION_PERMISSION
            imageDrawable = AppIcons.PermissionsPattern
            bgColor = backgroundColor
        }

        addSlide(AppIntroFragment.newInstance(sliderPage))

        askForPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        showSkipButton(false)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}