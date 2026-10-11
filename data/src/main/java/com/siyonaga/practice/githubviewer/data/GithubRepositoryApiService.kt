package com.siyonaga.practice.githubviewer.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GithubRepositoryApiService {
    @GET("/search/repositories?sort=stars")
    suspend fun getGithubRepositories(
        @Query("q") word: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") nowPage: Int,
    ): GithubRepositories

    @GET("/repos/{owner}/{repo}/readme")
    suspend fun getGithubRepositoryDetail(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String,
    ): GithubRepositoryDetail
}
