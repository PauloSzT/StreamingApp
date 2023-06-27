package com.example.streaming.ui.mediaplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.streaming.R
import com.example.streaming.ui.models.UiSongMedia
import com.example.streaming.ui.theme.StreamingAppTheme
import com.example.streaming.ui.utils.TestConstants.BTN_PLAY
import com.example.streaming.ui.utils.TestConstants.BTN_REWIND
import com.example.streaming.ui.utils.TestConstants.BTN_SKIP
import com.example.streaming.ui.utils.TestConstants.SONG_IMAGE
import com.example.streaming.ui.utils.TestConstants.SONG_NAME
import com.example.streaming.ui.utils.TestConstants.SONG_SLIDER
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MediaPlayerScreen(
    viewModel: MediaPlayerScreenViewModel = hiltViewModel(),
) {
    MediaPlayerScreenContent(viewModel.mediaPlayerUiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPlayerScreenContent(
    mediaPlayerUiState: MediaPlayerUiState
) {
    val songDetail by mediaPlayerUiState.songDetail.collectAsState()
    val seekbarPosition by mediaPlayerUiState.seekbarPosition.collectAsState()
    val isCurrentlyPlaying by mediaPlayerUiState.isCurrentlyPlaying.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize().testTag(SONG_IMAGE),
                    contentScale = ContentScale.FillBounds,
                    model = songDetail?.image ?: "",
                    contentDescription = null
                )
                Slider(
                    modifier = Modifier.testTag(SONG_SLIDER),
                    value = seekbarPosition,
                    onValueChange = {},
                    enabled = false,
                    valueRange = 0f..(songDetail?.duration?.times(1000) ?: 0f),
                    thumb = {
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(5.dp),
                            color = Color.Red
                        )
                    },
                    track = { state ->
                        SliderDefaults.Track(
                            colors = SliderDefaults.colors(
                                activeTrackColor = Color.Transparent,
                                inactiveTrackColor = Color.Transparent,
                                activeTickColor = Color.Transparent,
                                inactiveTickColor = Color.Transparent
                            ),
                            sliderState = state,
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(top = 24.dp).testTag(SONG_NAME),
                text = songDetail?.name ?: "songName",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier.testTag(BTN_PLAY),
                onClick = {
                    mediaPlayerUiState.playBtnClick()
                },
                elevation = ButtonDefaults.buttonElevation(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isCurrentlyPlaying) R.drawable.ic_pause_circle else R.drawable.ic_play_arrow
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.testTag(BTN_REWIND),
                    onClick = {
                        mediaPlayerUiState.rewindBtnClick()
                    },
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_backward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
                Button(
                    modifier = Modifier.testTag(BTN_SKIP),
                    onClick = {
                        mediaPlayerUiState.skipBtnClick()
                    },
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaPlayerScreenPreview() {
    val mediaPlayerUiState = MediaPlayerUiState(
        songDetail = MutableStateFlow(
            UiSongMedia(
                id = 641428,
                name = "Reggae synthesizer dance",
                duration = 89f,
                username = "Duisterwho",
                preview = "",
                image = "https://cdn.freesound.org/displays/641/641428_13590673_wave_M.png"
            )
        ),
        isCurrentlyPlaying = MutableStateFlow(true),
        seekbarPosition = MutableStateFlow(1f),
        playBtnClick = {},
        skipBtnClick = {},
        rewindBtnClick = {}
    )
    StreamingAppTheme {
        MediaPlayerScreenContent(mediaPlayerUiState = mediaPlayerUiState)
    }
}
