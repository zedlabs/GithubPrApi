package com.example.githubapi.data

import com.example.githubapi.models.PrListItem
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mediaApi: JsonApi
) {

    suspend fun getPullRequests(
        owner: String,
        repo: String,
        page: Int,
    ): List<PrListItem>? {
        return mediaApi.getPullRequests(owner, repo, page)
    }
}