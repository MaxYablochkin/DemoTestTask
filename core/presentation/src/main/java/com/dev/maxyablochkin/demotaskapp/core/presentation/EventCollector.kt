package com.dev.maxyablochkin.demotaskapp.core.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@SuppressLint("ComposableNaming")
@Composable
private fun <T> Flow<T>.collectAsEventWithLifecycle(
    vararg keys: Any?,
    minActiveState: Lifecycle.State,
    block: suspend (event: T) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val onEvent by rememberUpdatedState(block)

    LaunchedEffect(this, *keys, minActiveState, lifecycle) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            this@collectAsEventWithLifecycle.collect { onEvent(it) }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsUiEventWithLifecycle(
    vararg keys: Any?,
    block: suspend (event: T) -> Unit
) = collectAsEventWithLifecycle(*keys, minActiveState = Lifecycle.State.STARTED, block = block)

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsNavEventWithLifecycle(
    vararg keys: Any?,
    block: suspend (event: T) -> Unit
) = collectAsEventWithLifecycle(*keys, minActiveState = Lifecycle.State.RESUMED, block = block)