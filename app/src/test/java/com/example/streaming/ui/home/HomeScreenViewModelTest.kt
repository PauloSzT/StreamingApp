package com.example.streaming.ui.home

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSearchResultSong
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
import com.example.streaming.App
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var uiState: HomeScreenUiState

    private val getSongsBySearchUseCase: GetSongsBySearchUseCase = mockk()
    private val app: App = mockk()
    private val mockedSearchValueExecutor = "TestingSearchValueExecutor"
    private val mockedCoreSearchResult = CoreSearchResult(
        next = "1",
        results = listOf(
            (CoreSearchResultSong(
                id = 1,
                name = "TestResultSongName",
                username = "TestResultSongUsername"
            )),
            (CoreSearchResultSong(
                id = 2,
                name = "TestResultSongName",
                username = "TestResultSongUsername"
            )),
            (CoreSearchResultSong(
                id = 3,
                name = "TestResultSongName",
                username = "TestResultSongUsername"
            )),
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        coEvery {
            getSongsBySearchUseCase(
                any(), any()
            )
        } returns mockedCoreSearchResult
        viewModel = HomeScreenViewModel(
            getSongsBySearchUseCase,
            app
        )
        uiState = viewModel.homeScreenUiState
    }

    @Test
    fun `onQueryChange is called, searchValue is changed`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.searchValue.collect()
            }
            viewModel.homeScreenUiState.onQueryChange("TestQuery1")
            val newQuery = viewModel.homeScreenUiState.searchValue.first()
            assertEquals("TestQuery1", newQuery)
            collectJob.cancel()
        }

    @Test
    fun `If users types an space as first character, is omitted`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.searchValue.collect()
            }
            viewModel.homeScreenUiState.onQueryChange(" ")
            val newQuery = viewModel.homeScreenUiState.searchValue.first()
            assertEquals("", newQuery)
            collectJob.cancel()
        }

    @Test
    fun `paginatedSongProvider returns non null PagerInstance`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.paginatedSongProvider
            }
            val newSongPagingSource = getSongsBySearchUseCase(mockedSearchValueExecutor,"1")
            assertEquals(mockedCoreSearchResult,newSongPagingSource)
            collectJob.cancel()
        }

    @Test
    fun `paginatedSongProvider returns Null when SearchValue is empty`() =
        runTest {
            viewModel.searchValueExecutor.value = ""
            val paginatedSongProvider = viewModel.homeScreenUiState.paginatedSongProvider.value
            assertNull(paginatedSongProvider)
        }

    @Test
    fun `When onImeActionClick is called, sets IsLoading to true`() {
        viewModel.homeScreenUiState.onImeActionClick()
        assertEquals(true, viewModel.homeScreenUiState.isLoading.value)
    }

    @Test
    fun `When onImeActionClick is called, sets SearchValueExecutor to SearchValue`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.searchValue.collect()
            }
            viewModel.homeScreenUiState.onQueryChange("TestQuery1")
            val newQuery = viewModel.homeScreenUiState.searchValue.first()
            viewModel.homeScreenUiState.onImeActionClick()
            assertThat(viewModel.searchValueExecutor.value).isEqualTo(newQuery)
            collectJob.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `paginatedAnimeProvider is working`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.paginatedSongProvider.collect()
            }
            val mockedQuery = mockedSearchValueExecutor
            assertEquals(
                mockedCoreSearchResult,
                getSongsBySearchUseCase(mockedQuery , "1")
            )
            collectJob.cancel()
        }
}
