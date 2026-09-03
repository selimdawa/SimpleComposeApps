package com.flatcode.simplecomposeapps.wordpress.utils

import android.webkit.WebView
import android.webkit.WebViewClient

fun WebView.loadWordPressContent(content: String?) {
    val htmlContent = """
        <html>
        <head>
            <style>
                body {
                    background-color: transparent;
                    color: white;
                    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
                    line-height: 1.6;
                }
                .content img {
                    max-width: 100%;
                    height: auto;
                    border-radius: 8px;
                }
                a {
                    color: #BB86FC;
                }
            </style>
        </head>
        <body>
            <div class="content">$content</div>
        </body>
        </html>
    """.trimIndent()

    this.apply {
        settings.apply {
            loadsImagesAutomatically = true
            javaScriptEnabled = false
        }
        webViewClient = WebViewClient()
        loadDataWithBaseURL(
            "file:///android_asset/*",
            htmlContent,
            "text/html; charset=utf-8",
            "UTF-8",
            null
        )
    }
}