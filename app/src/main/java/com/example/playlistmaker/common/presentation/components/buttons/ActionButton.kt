package com.example.playlistmaker.common.presentation.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            colors = buttonColors(
                containerColor = colorResource(id = R.color.background_inverted),
                contentColor = colorResource(id = R.color.text_inverted)
            ),
            shape = RoundedCornerShape(
                dimensionResource(id = R.dimen.radius_xxxl)
            ),
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_sm),
                    vertical = dimensionResource(id = R.dimen.padding_xss)
                )

        ) {
            Text(
                text = text,
                style = TextStyle(
                    color = Color.Unspecified,
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = Dimens.textM
                ),
            )
        }
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton("Some text", {})
}