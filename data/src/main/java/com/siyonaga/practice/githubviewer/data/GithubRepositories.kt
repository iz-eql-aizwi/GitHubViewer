package com.siyonaga.practice.githubviewer.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GithubRepositories(
    @SerialName("items") val items: List<GithubRepository>,
)
