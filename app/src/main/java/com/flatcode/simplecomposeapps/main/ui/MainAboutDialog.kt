package com.flatcode.simplecomposeapps.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.main.MainInfoViewModel

@Composable
fun MainAboutDialog(
    infoViewModel: MainInfoViewModel, onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val mcBgColor = rememberColorAttr(context, "mc_bg")
    val infoItems by infoViewModel.dataMainInfo.observeAsState(emptyList())

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(250.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mcBgColor)
            ) {
                Text(
                    text = stringResource(id = R.string.app_features_mvvm),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                        start = 5.dp, end = 5.dp, bottom = 10.dp
                    ), verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(infoItems) { item ->
                        MainInfoItem(item = item)
                    }
                }
            }
        }
    }
}