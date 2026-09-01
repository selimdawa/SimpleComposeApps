package com.flatcode.simplecomposeapps.joke.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.joke.model.Joke
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun JokeItem(joke: Joke) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val colorOnBackground = rememberAttributeColor("colorOnBackground", Color.White, themeId)
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = colorOnBackground),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 21.dp, vertical = 15.dp),
        ) {
            if (joke.type == "single") {
                Text(
                    text = joke.joke ?: DATA.EMPTY,
                    color = colorError,
                    fontSize = 16.sp,
                )
            } else {
                Text(
                    text = joke.setup ?: DATA.EMPTY,
                    color = colorError,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = joke.delivery ?: DATA.EMPTY,
                    color = colorError,
                    fontSize = 16.sp,
                )
            }
        }
    }
}