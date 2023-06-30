package com.example.streaming.ui.mediaplayer

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.media3.exoplayer.ExoPlayer
import com.example.core.models.CoreSongMedia
import com.example.core.usecases.remote.getsongbyidusecase.GetSongByIdUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MediaPlayerScreenViewModelTest {

    private lateinit var viewModel: MediaPlayerScreenViewModel
    private lateinit var uiState: MediaPlayerUiState

    private val getSongByIdUseCase: GetSongByIdUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val app: Application = mockk()
    private val player: ExoPlayer = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockedCoreSongMedia = CoreSongMedia(
        id = 1,
        name = "testSongName",
        duration = 80f,
        username = "TestSongUsername",
        previews = "TestSongPreviews",
        images = "TestSongImages"
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        coEvery { getSongByIdUseCase(any()) } returns mockedCoreSongMedia
        every { savedStateHandle.getStateFlow<Int>(any(), any()) } returns MutableStateFlow(0)
        every { player.play() } just runs
        every { player.pause() } just runs
        every { player.currentPosition } returns 0
        every { player.seekToDefaultPosition() } just runs
        every { player.addListener(any()) } just runs
        every { player.seekTo(any()) } just runs
        every { player.setMediaSource(any()) } just runs
        every { player.prepare() } just runs
        every { player.playWhenReady } returns true

        viewModel = MediaPlayerScreenViewModel(
            getSongByIdUseCase,
            player,
            app,
            savedStateHandle
        )
        uiState = viewModel.mediaPlayerUiState
        viewModel.seekBarShouldRun = false
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When playBtnClick is called, player changes to pause`() =
        runTest {
            every { player.isPlaying } returns true
            viewModel.mediaPlayerUiState.playBtnClick()
            verify { player.pause() }
        }

    @Test
    fun `When playBtnClick is called and player is not playing, player changes to play`() =
        runTest {
            every { player.isPlaying } returns false
            viewModel.mediaPlayerUiState.playBtnClick()
            verify { player.play() }
        }

    @Test
    fun `skipBtnClick is called, player position go up to +15000 milliseconds`() =
        runTest {
            every { player.isPlaying } returns true
            viewModel.mediaPlayerUiState.skipBtnClick()
            verify { player.seekTo(player.currentPosition.plus(15000)) }
        }

    @Test
    fun `rewindBtnClick is called, player position go down to -15000 milliseconds`() =
        runTest {
            every { player.isPlaying } returns true
            viewModel.mediaPlayerUiState.rewindBtnClick()
            verify { player.seekTo(player.currentPosition.minus(15000)) }
        }
}
