package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val feedState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        homeState = feedState,
        onAction = viewModel::onAction
    )
}