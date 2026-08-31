package com.flatcode.simplecomposeapps.newsapp

import com.flatcode.simplecomposeapps.newsapp.model.NewsHeadlines

interface SelectListener {
    fun onNewsClicked(headlines: NewsHeadlines?)
}