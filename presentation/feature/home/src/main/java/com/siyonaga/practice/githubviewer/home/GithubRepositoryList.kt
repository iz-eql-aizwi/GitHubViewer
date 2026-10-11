package com.siyonaga.practice.githubviewer.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.siyonaga.practice.githubviewer.model.GithubRepository

@Composable
internal fun GithubRepositoryList(
    lazyPagingItems: LazyPagingItems<GithubRepository>,
    modifier: Modifier = Modifier,
    onClickListItem: (GithubRepository) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey(),
        ) { index ->
            val githubRepository = lazyPagingItems[index]

            githubRepository?.let {
                GithubRepositoryListItem(
                    githubRepository = githubRepository,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClickListItem = onClickListItem,
                )
            }
        }
    }
}
