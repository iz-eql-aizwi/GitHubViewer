package com.siyonaga.practice.githubviewer.model

data class GithubRepository(
    val name: String,
    // ownerName/repoName の形式の値
    val repositoryPath: String,
    val owner: Owner,
    val stargazersCount: Int,
)
