package com.example.streaming.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.streaming.ui.models.UiSearchResult
import com.example.streaming.ui.models.UiSearchResultSong
import java.io.IOException

class SongPagingSource (
    val stopLoadingState: () -> Unit,
    val getRemoteList: suspend (Int) -> UiSearchResult?
) : PagingSource<Int, UiSearchResultSong>() {

    override fun getRefreshKey(state: PagingState<Int, UiSearchResultSong>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiSearchResultSong> {
        return try {
            val pageNumber = params.key ?: 1
            val response = getRemoteList(pageNumber)
            stopLoadingState()
            if(response != null){
                LoadResult.Page(
                    data = response.results,
                    null,
                    nextKey = if (response.next != null) pageNumber + 1 else null
                )
            } else{
                LoadResult.Page(emptyList(),null, null)
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
