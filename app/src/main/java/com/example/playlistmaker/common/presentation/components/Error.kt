package com.example.playlistmaker.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.buttons.ActionButton

enum class ErrorType {
    WIFI,
    NOT_FOUND
}
@Composable
fun ComposeErrorView(state: ErrorState, onRefresh: (() -> Unit)? = null) {
    var text = ""
    var icon = 0

    when (state) {
        is ErrorState.Empty -> {
            text = state.text
            icon = R.drawable.ic_error_empty
        }
        is ErrorState.Wifi -> {
            text = state.text
            icon = R.drawable.ic_error_wifi
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = Dimens.padding9XL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_xxxl))
        )
        Text(
            text = text,
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize   = Dimens.textL,
            color      = colorResource(id = R.color.text_error),
            textAlign  = androidx.compose.ui.text.style.TextAlign.Center,
            modifier   = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_m),
                    start = dimensionResource(id = R.dimen.padding_xl),
                    end = dimensionResource(id = R.dimen.padding_xl)
                )
        )

        if (onRefresh != null) {
            ActionButton(text = stringResource(R.string.search_refresh), onRefresh)
        }
    }


}


@Preview
@Composable
fun ErrorPreview() {
    Column (Modifier.background(color = Color.White)){
        ComposeErrorView(ErrorState.Wifi("Some Text"))
        ComposeErrorView(ErrorState.Empty("Some Text"), {})
    }

}
