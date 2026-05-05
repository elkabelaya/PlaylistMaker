import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.components.searchbar.mockSequence


public class TrackPreviewProvider() : PreviewParameterProvider<Track> {
    override val values = Track.mockSequence()
}



