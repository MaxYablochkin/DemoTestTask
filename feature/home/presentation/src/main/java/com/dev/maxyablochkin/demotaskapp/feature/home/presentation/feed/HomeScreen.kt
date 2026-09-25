@file:OptIn(ExperimentalMaterial3Api::class)

package com.dev.maxyablochkin.demotaskapp.feature.home.presentation.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Article
import com.dev.maxyablochkin.demotaskapp.core.domain.model.Source
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.mapper.toUiModel
import com.dev.maxyablochkin.demotaskapp.feature.home.presentation.model.ArticleUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeState: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val listState = rememberLazyListState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()

    val textFieldState = rememberTextFieldState(initialText = homeState.searchQuery)

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .collectLatest { query ->
                if (query != homeState.searchQuery) {
                    onAction(HomeAction.OnSearchQueryChanged(query))
                }
            }
    }

    LaunchedEffect(homeState.searchQuery) {
        if (homeState.searchQuery.isEmpty() && textFieldState.text.isNotEmpty()) {
            textFieldState.clearText()
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItem >= totalItems - 3
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !homeState.isLoading && homeState.hasMore) {
            onAction(HomeAction.OnLoadMore)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarWithSearch(
                state = searchBarState,
                scrollBehavior = scrollBehavior,
                inputField = {
                    SearchBarDefaults.InputField(
                        textFieldState = textFieldState,
                        searchBarState = searchBarState,
                        onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                        placeholder = { Text("Пошук новин...") },
                        leadingIcon = { Text("🔍") },
                        trailingIcon = {
                            if (textFieldState.text.isNotEmpty()) {
                                IconButton(onClick = {
                                    textFieldState.clearText()
                                    onAction(HomeAction.OnClearSearch)
                                }) {
                                    Text("❌")
                                }
                            }
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = homeState.isLoading && homeState.articles.isEmpty(),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            val displayList = if (homeState.isSearching) homeState.searchResults else homeState.articles

            if (displayList.isEmpty() && homeState.searchQuery.isNotBlank() && !homeState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "За вашим запитом нічого не знайдено",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = displayList,
                        key = { it.url.ifEmpty { it.id } }
                    ) { article ->
                        val uiModel = remember(article) { article.toUiModel() }
                        ArticleItem(article = uiModel)
                    }

                    if (homeState.isLoading && displayList.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(
    article: ArticleUiModel,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (article.imageUrl.isNotBlank()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = article.imageUrl,
                contentDescription = "Article image",
                contentScale = ContentScale.Crop,
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (article.sourceName.isNotBlank()) {
                        Text(
                            text = article.sourceName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (article.formattedDate.isNotBlank()) {
                        Text(
                            text = article.formattedDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        homeState = HomeState(
            articles = listOf(
                Article(
                    id = "1",
                    title = "Rare September Nor'easter set to slam East Coast",
                    urlToImage = "",
                    source = Source(id = null, name = "NBC News"),
                    publishedAt = "2026-09-24T13:40:19Z",
                    content = "Millions across the Northeast...",
                    url = "https://example.com/1"
                ),
                Article(
                    id = "2",
                    title = "One blood test could screen you for 50 cancers",
                    urlToImage = "",
                    source = Source(id = null, name = "The Washington Post"),
                    publishedAt = "2026-09-24T13:33:10Z",
                    content = "A divided panel narrowly backed a controversial test...",
                    url = "https://example.com/2"
                )
            ),
            searchQuery = "",
            isLoading = false
        ),
        onAction = {}
    )
}

@Preview
@Composable
private fun ArticleItemPreview() {
    ArticleItem(
        article = ArticleUiModel(
            id = "1",
            title = "Apple Releases Second watchOS Beta for Developers",
            imageUrl = "",
            sourceName = "MacRumors",
            formattedDate = "2026-09-24",
            content = "Sample content...",
            url = "https://example.com"
        )
    )
}