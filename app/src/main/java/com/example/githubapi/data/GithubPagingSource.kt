package com.example.githubapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubapi.models.PrListItem

class GithubPagingSource(
    private val network: MainRepository,
    private val owner: String,
    private val repo: String,
) : PagingSource<Int, PrListItem>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PrListItem> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = network.getPullRequests(owner, repo, nextPageNumber)
            LoadResult.Page(
                data = response ?: listOf(),
                prevKey = null, // Only paging forward.
                nextKey = if (response.isNullOrEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PrListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}