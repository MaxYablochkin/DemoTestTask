package com.dev.maxyablochkin.demotaskapp.feature.categories.presentation.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesRoot(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}
