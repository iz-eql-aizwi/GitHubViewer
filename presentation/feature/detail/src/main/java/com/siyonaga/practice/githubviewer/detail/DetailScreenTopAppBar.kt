package com.siyonaga.practice.githubviewer.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreenTopAppBar(
    repoName: String,
    onNavigateToHome: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = repoName) },
        navigationIcon = {
            IconButton(onClick = onNavigateToHome) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_to_home),
                )
            }
        },
    )
}
