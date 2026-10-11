package com.siyonaga.practice.githubviewer.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.siyonaga.practice.githubviewer.model.GithubRepository

@Composable
internal fun GithubRepositoryListItem(
    githubRepository: GithubRepository,
    modifier: Modifier = Modifier,
    onClickListItem: (GithubRepository) -> Unit,
) {
    Row(
        modifier = modifier.clickable {
            onClickListItem(githubRepository)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = githubRepository.owner.profileIconUrl,
            contentDescription = githubRepository.owner.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(40.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Text(text = githubRepository.name)
            Row {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFF2D602),
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = githubRepository.stargazersCount.toString())
            }
        }
    }
}
