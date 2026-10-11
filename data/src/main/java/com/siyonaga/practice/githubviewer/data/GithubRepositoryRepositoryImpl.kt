package com.siyonaga.practice.githubviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.siyonaga.practice.githubviewer.model.GithubRepository
import com.siyonaga.practice.githubviewer.model.GithubRepositoryDetail
import com.siyonaga.practice.githubviewer.model.Order
import com.siyonaga.practice.githubviewer.model.ReadmeNotFoundException
import com.siyonaga.practice.githubviewer.repository.GithubRepositoryRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

internal class GithubRepositoryRepositoryImpl @Inject constructor(
    private val githubRepositoryApiService: GithubRepositoryApiService,
) : GithubRepositoryRepository {
    override fun getGithubRepositories(
        word: String,
        order: Order,
    ): Flow<PagingData<GithubRepository>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 30,
            ),
        ) {
            GithubRepositoriesPagingSource(
                githubRepositoryApiService,
                word,
                order.orderName,
            )
        }.flow
    }

    override suspend fun getGithubRepositoryDetail(
        ownerName: String,
        repoName: String,
    ): GithubRepositoryDetail {
        val githubRepositoryDetail = runCatching {
            githubRepositoryApiService.getGithubRepositoryDetail(
                ownerName = ownerName,
                repoName = repoName,
            )
        }.fold(
            onSuccess = {
                it
            },
            onFailure = {
                if ((it as? HttpException)?.code() == 404) {
                    throw ReadmeNotFoundException()
                } else {
                    throw it
                }
            },
        )

        return GithubRepositoryDetail(readmeText = githubRepositoryDetail.readmeText())
    }
}
