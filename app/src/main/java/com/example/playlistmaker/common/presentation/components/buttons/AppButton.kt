package com.example.playlistmaker.common.presentation.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = buttonColors(
            containerColor = colorResource(id = R.color.bg_app_button_colors),
            contentColor = colorResource(id = R.color.yp_white)
        ),
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.radius_xs)
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontFamily = appFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize   = Dimens.textM
            ),
            maxLines = 1,

        )
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    Column {
        AppButton("Some text", {})
    }

}