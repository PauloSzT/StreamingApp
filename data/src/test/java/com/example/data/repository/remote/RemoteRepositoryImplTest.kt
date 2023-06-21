package com.example.data.repository.remote


import com.example.core.models.CoreSearchResult
import com.example.core.models.CoreSearchResultSong
import com.example.core.models.CoreSongMedia
import com.example.data.models.RemoteSearchResult
import com.example.data.models.RemoteSearchResultSong
import com.example.data.models.RemoteSongImage
import com.example.data.models.RemoteSongMedia
import com.example.data.models.RemoteSongPreview
import com.example.data.remote.SongApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteRepositoryImplTest {

    private lateinit var fakeRemoteRepositoryImpl: RemoteRepositoryImpl
    private val fakeApiService: SongApiService = mockk()

    private val mockedRemoteSongPreview = RemoteSongPreview(
        previewHqMp3 = "TestPreviewSong1"
    )

    private val mockedRemoteSongImage = RemoteSongImage(
        remoteImageWaveFormM = "TestSongImage1"
    )

    private val mockedRemoteSongMedia = RemoteSongMedia(
        id = 1,
        name = "TestSongName1",
        duration = 20f,
        username = "TestSongUsername1",
        previews = mockedRemoteSongPreview,
        images = mockedRemoteSongImage
    )

    private val mockedRemoteSearchResult = RemoteSearchResult(
        next = "TestSearchResultNext1",
        results = listOf(
            RemoteSearchResultSong(
                id = 1,
                name = "TestSearchResultSongName1",
                username = "TestSearchResultSongUsername1"
            ),
            RemoteSearchResultSong(
                id = 2,
                name = "TestSearchResultSongName2",
                username = "TestSearchResultSongUsername2"
            ),
            RemoteSearchResultSong(
                id = 3,
                name = "TestSearchResultSongName3",
                username = "TestSearchResultSongUsername3"
            ),
            RemoteSearchResultSong(
                id = 4,
                name = "TestSearchResultSongName4",
                username = "TestSearchResultSongUsername4"
            )
        )
    )


    private val mockedCoreSongMedia = CoreSongMedia(
        id = 1,
        name = "TestSongName1",
        duration = 20f,
        username = "TestSongUsername1",
        previews = "TestPreviewSong1",
        images = "TestSongImage1"
    )

    private val mockedCoreSearchResult = CoreSearchResult(
        next = "TestSearchResultNext1",
        results = listOf(
            CoreSearchResultSong(
                id = 1,
                name = "TestSearchResultSongName1",
                username = "TestSearchResultSongUsername1"
            ),
            CoreSearchResultSong(
                id = 2,
                name = "TestSearchResultSongName2",
                username = "TestSearchResultSongUsername2"
            ),
            CoreSearchResultSong(
                id = 3,
                name = "TestSearchResultSongName3",
                username = "TestSearchResultSongUsername3"
            ),
            CoreSearchResultSong(
                id = 4,
                name = "TestSearchResultSongName4",
                username = "TestSearchResultSongUsername4"
            )
        )
    )

    @Before
    fun setUp() {
        fakeRemoteRepositoryImpl = RemoteRepositoryImpl(fakeApiService)

        coEvery { fakeApiService.getSongById(any()) } returns Response.success(mockedRemoteSongMedia)
        coEvery { fakeApiService.getSongsBySearch(any(), any()) } returns Response.success(mockedRemoteSearchResult)
    }

    @Test
    fun `When getting song by id, item return correctly`() = runBlocking {
        val newSong = fakeRemoteRepositoryImpl.getSongById(songId = 1)
        assertEquals(newSong, mockedCoreSongMedia)
    }

    @Test
    fun `When getting songs by search, items return correctly`()= runBlocking {
        val newSongListBySearch = fakeRemoteRepositoryImpl.getSongsBySearch(
            query = "goku",
            page = "1"
        )
        assertEquals(newSongListBySearch, mockedCoreSearchResult)
    }
}
