package com.example.streaming.ui.mediaplayer

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MusicSeekbar(
    mediaPlayerUiState: MediaPlayerUiState,
    modifier: Modifier = Modifier
) {
//    val seekBarValue by mediaPlayerUiState.seekbarPosition.collectAsState()
//    val seekBarRange = 0f..seekBarValue.second
//    Slider(
//        value = seekBarValue.first,
//        onValueChange = { newPosition ->
//            mediaPlayerUiState.onSeekbarChange(newPosition)
//        },
//        valueRange = seekBarRange,
//        modifier = modifier.width(200.dp),
//        colors = SliderDefaults.colors(
//            thumbColor = MaterialTheme.colorScheme.inversePrimary,
//            activeTrackColor = MaterialTheme.colorScheme.primary,
//            inactiveTrackColor = MaterialTheme.colorScheme.onBackground
//        )
//    )
}
