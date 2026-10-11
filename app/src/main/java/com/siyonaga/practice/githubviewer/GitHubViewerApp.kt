package com.siyonaga.practice.githubviewer

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.siyonaga.practice.githubviewer.detail.DetailScreen
import com.siyonaga.practice.githubviewer.detail.DetailViewModel
import com.siyonaga.practice.githubviewer.home.HomeScreen

@Composable
internal fun GitHubViewerApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            HomeScreen(
                viewModel = hiltViewModel(),
                onClickListItem = { githubRepository ->
                    navController.navigate(
                        DetailRoute(
                            ownerName = githubRepository.owner.name,
                            repoName = githubRepository.name,
                        ),
                    )
                },
            )
        }
        composable<DetailRoute> { backStackEntry ->
            val detail: DetailRoute = backStackEntry.toRoute()

            DetailScreen(
                repoName = detail.repoName,
                viewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory> {
                    it.create(ownerName = detail.ownerName, repoName = detail.repoName)
                },
                onNavigateToHome = {
                    navController.navigateUp()
                },
            )
        }
    }
}
