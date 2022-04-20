package com.mankart.mankgram.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mankart.mankgram.data.network.ApiService
import com.mankart.mankgram.model.StoryModel
import retrofit2.awaitResponse

class StoryPagingSource(private val apiService: ApiService, private val location: Int) : PagingSource<Int, StoryModel>() {
    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return try {
            Log.e("StoryPagingSource", "fun load on ${Thread.currentThread().name} in StoryPagingSource")
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getUserStories(location, page, params.loadSize).awaitResponse().body()
            val responseData = response?.listStory as List<StoryModel>

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE_INDEX = 1
    }
}