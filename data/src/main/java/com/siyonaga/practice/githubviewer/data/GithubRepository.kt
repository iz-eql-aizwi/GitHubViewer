package com.siyonaga.practice.githubviewer.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GithubRepository(
    @SerialName("name") val name: String,
    // ownerName/repoName の形式の値
    @SerialName("full_name") val fullName: String,
    @SerialName("owner") val owner: Owner,
    @SerialName("stargazers_count") val stargazersCount: Int,
)
