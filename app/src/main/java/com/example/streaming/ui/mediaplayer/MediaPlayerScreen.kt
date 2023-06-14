package com.example.streaming.ui.mediaplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.streaming.R

@Composable
fun MediaPlayerScreen(
    viewModel: MediaPlayerScreenViewModel
){
    MediaPlayerScreenContent(viewModel.mediaPlayerUiState)
}

@Composable
fun MediaPlayerScreenContent(
    mediaPlayerUiState: MediaPlayerUiState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Media Player",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 3.dp)
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .aspectRatio(4f),
                painter = painterResource(R.drawable.ic_music),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxHeight(0.15f),
                text = "SongName",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            MusicSeekbar(
                mediaPlayerUiState,
                modifier = Modifier.fillMaxHeight(0.1f),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
//                    playUiState.pauseOrResume()
                          },
                elevation = ButtonDefaults.buttonElevation(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier.fillMaxHeight(0.25f),
            ) {
                Icon(
                    painter = painterResource(
                        id = if (true) R.drawable.ic_pause_circle else R.drawable.ic_play_arrow
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .height(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
//                        playUiState.skipPrev()
                              },
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_backward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
                Button(
                    onClick = {
//                        playUiState.skipNext()
                              },
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
