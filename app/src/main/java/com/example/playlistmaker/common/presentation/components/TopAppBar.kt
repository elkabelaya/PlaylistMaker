package com.example.playlistmaker.common.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, onBack: (() -> Unit)? = null) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource( R.color.background),
            titleContentColor = colorResource(R.color.text_toolbar)
        ),
        title = { Text(title,
            color = colorResource(R.color.text_toolbar),
            fontSize = Dimens.textXL,
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
        ) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = { onBack() }) {
                    Icon(painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = colorResource(R.color.text_toolbar)
                    )
                }
            }
        },
        //modifier = Modifier.background(colorResource( R.color.background))
    )
}


@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TopAppBarPreview() {
    Column {
        TopAppBar("Поиск")
        TopAppBar("Поиск", {})
    }
}
