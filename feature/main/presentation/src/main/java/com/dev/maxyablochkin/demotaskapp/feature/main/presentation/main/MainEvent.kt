package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

sealed interface MainNavEvent {
    data object Home : MainNavEvent
    data object Categories : MainNavEvent
}
