import android.content.Context
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Track

public class TrackPreviewProvider() : PreviewParameterProvider<Track> {
    private var loremIpsum = LoremIpsum(10).values.first()
    override val values = sequenceOf(
        Track(
        1,
            trackName = "The Show Must Go On",
            artistName = "Queen",
            trackTime = "04:32",
            imageUrl = null,
            coverUrl = null,
            collectionName = "Innuendo",
            primaryGenreName = "",
            country = "Great Britain",
            previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
            year = "1991"
        ),
        Track(
        1,
            trackName = loremIpsum,
            artistName = loremIpsum,
            trackTime = "04:32",
            imageUrl = null,
            coverUrl = null,
            collectionName = loremIpsum,
            primaryGenreName = "",
            country = loremIpsum,
            previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
            year = "1991"
        ),
        Track(
            1,
            trackName = "artistName = null",
            artistName = null,
            trackTime = "04:32",
            imageUrl = null,
            coverUrl = null,
            collectionName = "Innuendo",
            primaryGenreName = "",
            country = "Great Britain",
            previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
            year = "1991"
        ),
        Track(
            1,
            trackName = "trackTime is empty",
            artistName = "Queen",
            trackTime = "",
            imageUrl = null,
            coverUrl = null,
            collectionName = "Innuendo",
            primaryGenreName = "",
            country = "Great Britain",
            previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
            year = "1991"
        ),
        Track(
            1,
            trackName = "artistName and trackTime are empty",
            artistName = null,
            trackTime = "",
            imageUrl = null,
            coverUrl = null,
            collectionName = "Innuendo",
            primaryGenreName = "",
            country = "Great Britain",
            previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
            year = "1991"
        ),
    )
}

