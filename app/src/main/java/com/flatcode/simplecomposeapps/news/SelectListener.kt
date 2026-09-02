package com.flatcode.simplecomposeapps.news

import com.flatcode.simplecomposeapps.news.model.NewsHeadlines

interface SelectListener {
    fun onNewsClicked(headlines: NewsHeadlines?)
}