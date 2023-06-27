package com.example.streaming.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.streaming.R
import com.example.streaming.ui.models.UiSearchResultSong
import com.example.streaming.ui.utils.TestConstants.RESULT_ITEM_ROW

@Composable
fun SearchItemCard(
    uiSearchResultSong: UiSearchResultSong,
    navigateToMediaPlayer: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.padding(4.dp).testTag(RESULT_ITEM_ROW)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { navigateToMediaPlayer(uiSearchResultSong.id) },
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    painter = painterResource(id = R.drawable.ic_play_square),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${uiSearchResultSong.name} - ${uiSearchResultSong.username}",
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        minLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                }
            }
        }
    }
}
