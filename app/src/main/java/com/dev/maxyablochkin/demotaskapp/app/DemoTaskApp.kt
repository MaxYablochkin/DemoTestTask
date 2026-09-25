package com.dev.maxyablochkin.demotaskapp.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dev.maxyablochkin.demotaskapp.app.theme.DemoTaskAppTheme
import com.dev.maxyablochkin.demotaskapp.core.presentation.LocalSnackbarHostState

@Composable
fun DemoTaskApp() {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        DemoTaskAppTheme {
            Scaffold(
                contentWindowInsets = WindowInsets(),
                snackbarHost = { SnackbarHost(snackbarHostState, Modifier.navigationBarsPadding()) },
                content = { contentPadding ->
                    DemoTaskAppNavigation(Modifier.fillMaxSize().padding(contentPadding))
                }
            )
        }
    }
}