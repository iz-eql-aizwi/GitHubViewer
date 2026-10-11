package com.siyonaga.practice.githubviewer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.siyonaga.practice.githubviewer.model.GithubRepository
import com.siyonaga.practice.githubviewer.model.Order
import com.siyonaga.practice.githubviewer.repository.GithubRepositoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepositoryRepository: GithubRepositoryRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState.Initial)
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingData: Flow<PagingData<GithubRepository>> = uiState.mapNotNull {
        it as? HomeScreenUiState.Success
    }.flatMapLatest {
        githubRepositoryRepository.getGithubRepositories(
            word = it.word,
            order = it.order,
        )
    }.cachedIn(viewModelScope)

    init {
        _uiState.value = HomeScreenUiState.Success()
    }

    fun searchGithubRepositories(word: String) {
        _uiState.update {
            when (it) {
                is HomeScreenUiState.Initial -> it
                is HomeScreenUiState.Success -> {
                    it.copy(
                        word = word,
                    )
                }
            }
        }
    }

    fun changeOrder() {
        _uiState.update {
            when (it) {
                is HomeScreenUiState.Initial -> it
                is HomeScreenUiState.Success -> {
                    it.copy(
                        order = if (it.order == Order.DESC) Order.ASC else Order.DESC,
                    )
                }
            }
        }
    }
}
