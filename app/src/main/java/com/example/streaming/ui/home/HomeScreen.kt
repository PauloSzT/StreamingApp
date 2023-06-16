package com.example.streaming.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.streaming.R
import com.example.streaming.ui.navigation.NavItem

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    HomeScreenContent(viewModel.homeScreenUiState) { songId ->
        navHostController.navigate(NavItem.MediaPlayerScreen.routeWithArgs(songId))

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenContent(
    homeScreenUiState: HomeScreenUiState,
    navigateToMediaPlayer: (Int) -> Unit
) {
    val searchValue by homeScreenUiState.searchValue.collectAsState()
    val isLoading by homeScreenUiState.isLoading.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val paginatedSongProvider by homeScreenUiState.paginatedSongProvider.collectAsState()
    val paginatedSongs = paginatedSongProvider?.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row {
            TextField(
                value = searchValue,
                onValueChange = { value ->
                    homeScreenUiState.onQueryChange(value)
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            homeScreenUiState.onImeActionClick()
                            keyboardController?.hide()
                        },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    homeScreenUiState.onImeActionClick()
                    keyboardController?.hide()
                }),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                LoadingScreen()
            } else {
                if (paginatedSongs != null && paginatedSongs.itemCount > 0) {
                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                        items(
                            count = paginatedSongs.itemCount,
                            key = paginatedSongs.itemKey(),
                            contentType = paginatedSongs.itemContentType()
                        ) { index ->
                            val item = paginatedSongs[index]
                            item?.let { resultSong ->
                                SearchItemCard(
                                    uiSearchResultSong = resultSong,
                                    navigateToMediaPlayer = { navigateToMediaPlayer(resultSong.id) }
                                )
                            }
                        }
                    }
                } else {
                    NoResult()
                }
            }
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun NoResult() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.no_search_results))
    }
}
