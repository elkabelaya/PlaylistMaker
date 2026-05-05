package com.example.playlistmaker.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R

@Composable
fun ComposeSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .width(35.dp)
            .height(18.dp)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .width(35.dp)
                .height(14.dp)
                .background(colorResource(if (checked) R.color.switch_track  else R.color.yp_light_gray) , RoundedCornerShape(12.dp))
                .padding(2.dp)
        )
        Box(
            modifier = Modifier
                .size(18.dp) // Adjust thumb size
                .background(colorResource(if (checked) R.color.switch_thumb else R.color.yp_text_gray),
                    RoundedCornerShape(12.dp))

        )
    }
}

@Preview
@Composable
fun ComposeSwitchPreview() {
    Column {
        ComposeSwitch(true) {}
        ComposeSwitch(false) {}
    }

}