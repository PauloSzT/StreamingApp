package com.example.streaming.ui.home

import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSearchResultSong
import com.example.core.usecases.remote.getsongsbysearchusecase.GetSongsBySearchUseCase
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
            getSongsBySearchUseCase
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
    fun `If users types an space as first character, is omited`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.homeScreenUiState.searchValue.collect()
            }
            viewModel.homeScreenUiState.onQueryChange(" ")
            val newQuery = viewModel.homeScreenUiState.searchValue.first()
            assertEquals("", newQuery)
            collectJob.cancel()
        }

//    @Test
//    fun `paginatedSongProvider returns non null PagerInstance`() =
//        runTest {
//            viewModel.searchValueExecutor.value = "rock"
//            val paginatedSongProvider = viewModel.paginatedSongProvider.value
//            assertNotNull(paginatedSongProvider)
//        }

    @Test
    fun `paginatedSongProvider returns Null when SearchValue is empty`() =
        runTest {
            viewModel.searchValueExecutor.value = ""
            val paginatedSongProvider = viewModel.paginatedSongProvider.value
            assertNull(paginatedSongProvider)
        }

//    @Test
//    fun onImeActionClick_shouldSetIsLoadingToTrueAndSearchValueExecutorToSearchQuery() =
//        runTest {
//            val collectJob = launch(testDispatcher) {
//                viewModel.homeScreenUiState.searchValue.collect()
//            }
//            // Given
//            val searchQuery = "test"
//            viewModel.searchValueExecutor.value = searchQuery
//            // When
//            viewModel.onImeActionClick()
//            val updateQuery = uiState.searchValue.value
//            // Then
//            coVerify(getSongsBySearchUseCase(searchQuery, "1"))
//            assertThat(viewModel.isLoading.value).isTrue()
//            assertEquals(searchQuery, updateQuery)
//            collectJob.cancel()
//        }
}
