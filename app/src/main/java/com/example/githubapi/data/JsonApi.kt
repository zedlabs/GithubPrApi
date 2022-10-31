package com.example.githubapi.data

import com.example.githubapi.models.PrListItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JsonApi {

    @GET("/repos/{owner}/{repo}/pulls")
    suspend fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("state") state: String = "all",
    ): List<PrListItem>?
}