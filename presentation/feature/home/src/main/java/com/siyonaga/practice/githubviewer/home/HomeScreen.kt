package com.siyonaga.practice.githubviewer.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.siyonaga.practice.githubviewer.model.ApiUnprocessableException
import com.siyonaga.practice.githubviewer.model.GithubRepository
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickListItem: (GithubRepository) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    val refreshLoadState = lazyPagingItems.loadState.refresh
    val appendLoadState = lazyPagingItems.loadState.append
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when (val state = uiState) {
        is HomeScreenUiState.Initial -> { /* 初期状態 */ }
        is HomeScreenUiState.Success -> {
            Scaffold(
                topBar = {
                    HomeScreenSearchBar(
                        word = state.word,
                        order = state.order,
                        enable = refreshLoadState !is LoadState.Error,
                        modifier = Modifier.fillMaxWidth(),
                        onSearchClicked = { word ->
                            viewModel.searchGithubRepositories(word)
                            if (refreshLoadState is LoadState.Error) {
                                lazyPagingItems.refresh()
                            }
                        },
                        onSortButtonClicked = {
                            viewModel.changeOrder()
                        },
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
            ) { contentPadding ->
                Box(
                    modifier = Modifier.padding(contentPadding),
                ) {
                    when (refreshLoadState) {
                        is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is LoadState.NotLoading -> {
                            if (lazyPagingItems.itemCount > 0) {
                                GithubRepositoryList(
                                    lazyPagingItems = lazyPagingItems,
                                    onClickListItem = onClickListItem,
                                )
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = stringResource(R.string.no_repository_text),
                                        modifier = Modifier.size(80.dp),
                                        tint = Color.Blue,
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.no_repository_text),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.suggest_search_word_text),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                    )
                                }
                            }
                            if (appendLoadState is LoadState.Error) {
                                val message = stringResource(R.string.data_fetch_limit_text)

                                LaunchedEffect(Unit) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = message,
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Long,
                                        )
                                    }
                                }
                            }
                        }
                        is LoadState.Error -> {
                            if (refreshLoadState.error is ApiUnprocessableException) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = stringResource(R.string.suggest_input_search_word_text),
                                        modifier = Modifier.size(80.dp),
                                        tint = Color.Blue,
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.suggest_input_search_word_text),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                    )
                                }
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = stringResource(R.string.connection_error_text),
                                        modifier = Modifier.size(80.dp),
                                        tint = Color.Red,
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = stringResource(R.string.connection_error_text),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                    TextButton(
                                        onClick = { lazyPagingItems.refresh() },
                                    ) {
                                        Text(text = stringResource(R.string.retry_text))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
