package com.siyonaga.practice.githubviewer

import kotlinx.serialization.Serializable

@Serializable
internal data class DetailRoute(
    val ownerName: String,
    val repoName: String,
)
