package com.example.playlistmaker.common.presentation.components.searchbar

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    showClearButton: Boolean,
    onClearClicked: () -> Unit
) {
    Column {
    BasicTextField(//can set custom contentPadding only in BasicTextField
        value = query,
        onValueChange = onQueryChanged,
        cursorBrush = SolidColor(colorResource(R.color.yp_blue)),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = Dimens.paddingM,
                vertical = Dimens.paddingXS
            )
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        decorationBox = @Composable { innerTextField ->
           TextFieldDefaults.DecorationBox(
                value = query,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = { Text(text = stringResource(id = R.string.main_search)) },
                label = null,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = colorResource(id = R.color.hint_search),
                        modifier = Modifier.size(Dimens.iconM)
                    )
                },
                trailingIcon = if (showClearButton) {
                    {
                        IconButton(
                            onClick = onClearClicked,

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                tint = colorResource(id = R.color.hint_search),
                                modifier = Modifier.size(Dimens.iconM)
                            )
                        }
                    }
                } else null,
                prefix = null,
                suffix = null,
                supportingText = null,
                shape = RoundedCornerShape(Dimens.radiusM),
                singleLine = true,
                enabled = true,
                isError = false,
                interactionSource = remember { MutableInteractionSource() },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = colorResource(id = R.color.text_search),
                    unfocusedTextColor = colorResource(id = R.color.text_search),
                    focusedContainerColor = colorResource(id = R.color.background_search),
                    unfocusedContainerColor = colorResource(id = R.color.background_search),
                    cursorColor = colorResource(id = R.color.yp_blue),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = colorResource(id = R.color.hint_search),
                    unfocusedLeadingIconColor = colorResource(id = R.color.hint_search),
                    focusedTrailingIconColor = colorResource(id = R.color.hint_search),
                    unfocusedTrailingIconColor = colorResource(id = R.color.hint_search),
                    focusedPlaceholderColor = colorResource(id = R.color.hint_search),
                    unfocusedPlaceholderColor = colorResource(id = R.color.hint_search),
                ),
            )
        },
    )

}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SearchScreenPreview() {
    Column {
        SearchBar(
            query = "124",
            onQueryChanged = {},
            onFocusChanged = {},
            showClearButton = true,
            onClearClicked = { }
        )

        SearchBar(
            query = "",
            onQueryChanged = {},
            onFocusChanged = {},
            showClearButton = false,
            onClearClicked = {}
        )
    }
}