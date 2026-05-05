package com.example.playlistmaker.common.presentation.components.track

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.Image


@Composable
fun TrackItem(track: Track, onClick: (Track) -> Unit) {
   Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.listItemHeight)
            .padding(horizontal = Dimens.paddingS)
            .clickable{onClick(track)},
       verticalAlignment = Alignment.CenterVertically
    ) {
           AsyncImage(
               model = ImageRequest.Builder(LocalContext.current)
                   .data(track.coverUrl)
                   .crossfade(true)
                   .build(),
               placeholder = painterResource(R.drawable.bg_placeholder),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .size(45.dp)
           )
            Spacer(Modifier.width(Dimens.paddingS))
            Column(modifier = Modifier.weight(1f) ) {

                Text(text = track.trackName,
                    color = colorResource(R.color.text),
                    fontSize = Dimens.textM,
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row {
                    if (!track.artistName.isNullOrEmpty()) {
                        Text(
                            text = track.artistName,
                            color = colorResource(R.color.text_secondary),
                            fontSize = Dimens.textXS,
                            fontFamily = appFontFamily,
                            fontWeight = FontWeight.Normal,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.wrapContentSize()
                                .weight(1f, fill = false)
                        )
                    }
                    if (!track.artistName.isNullOrEmpty() && track.trackTime.isNotEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_dot),
                            modifier = Modifier.size(Dimens.iconS),
                            colorFilter = ColorFilter.tint(colorResource(R.color.text_secondary))
                        )
                    }
                    Text(text = track.trackTime,
                        color = colorResource(R.color.text_secondary),
                        fontSize = Dimens.textXS,
                        fontFamily = appFontFamily,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
       Image(
           painter = painterResource(id = R.drawable.ic_arrow_right),
           modifier = Modifier
               .padding(start = Dimens.paddingXS, end = Dimens.paddingS)
               .size(Dimens.iconM),
           colorFilter = ColorFilter.tint(colorResource(R.color.text_secondary))
       )
   }
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TrackItemPreview(
    @PreviewParameter(TrackPreviewProvider::class) track: Track
) {
    TrackItem(track) {}
}
