package com.siyonaga.practice.githubviewer.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siyonaga.practice.githubviewer.model.ReadmeNotFoundException
import com.siyonaga.practice.githubviewer.repository.GithubRepositoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted("ownerName") private val ownerName: String,
    @Assisted("repoName") private val repoName: String,
    private val githubRepositoryRepository: GithubRepositoryRepository,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("ownerName") ownerName: String,
            @Assisted("repoName") repoName: String,
        ): DetailViewModel
    }

    private val _uiState: MutableStateFlow<DetailScreenUiState> =
        MutableStateFlow(DetailScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getGithubRepositoryDetail()
    }

    fun retry() {
        getGithubRepositoryDetail()
    }

    private fun getGithubRepositoryDetail() {
        viewModelScope.launch {
            _uiState.value = runCatching {
                val githubRepositoryDetail = githubRepositoryRepository
                    .getGithubRepositoryDetail(
                        ownerName = ownerName,
                        repoName = repoName,
                    )

                DetailScreenUiState.Success(githubRepositoryDetail.readmeText)
            }.fold(
                onSuccess = {
                    it
                },
                onFailure = {
                    if (it is ReadmeNotFoundException) {
                        DetailScreenUiState.Error(isNotFoundException = true)
                    } else {
                        DetailScreenUiState.Error(isNotFoundException = false)
                    }
                },
            )
        }
    }
}
