package com.siyonaga.practice.githubviewer.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.m3.Markdown

@Composable
fun DetailScreen(
    repoName: String,
    viewModel: DetailViewModel,
    onNavigateToHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DetailScreenTopAppBar(
                repoName = repoName,
                onNavigateToHome = onNavigateToHome,
            )
        },
    ) { contentPadding ->
        when (val state = uiState) {
            is DetailScreenUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailScreenUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Markdown(content = state.readmeText)
                }
            }

            is DetailScreenUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.isNotFoundException) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.no_readme_text),
                            modifier = Modifier.size(80.dp),
                            tint = Color.Blue,
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.no_readme_text),
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(
                            onClick = onNavigateToHome,
                        ) {
                            Text(text = stringResource(R.string.back_to_home_screen_text))
                        }
                    } else {
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
                            onClick = { viewModel.retry() },
                        ) {
                            Text(text = stringResource(R.string.retry_text))
                        }
                    }
                }
            }
        }
    }
}
