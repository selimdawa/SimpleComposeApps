package com.flatcode.simplecomposeapps.web

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.web.ui.WebViewScreen

class WebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val webName = intent.getStringExtra(DATA.WEB_NAME) ?: ""

        val url = when (webName) {
            DATA.WEBSITE -> DATA.mySite
            DATA.INSTAGRAM -> DATA.myInstagram
            DATA.FACEBOOK -> DATA.myFacebook
            DATA.TWITTER -> DATA.myTwitter
            else -> DATA.mySite
        }

        setContent {
            WebViewScreen(
                url = url
            )
        }
    }
}
