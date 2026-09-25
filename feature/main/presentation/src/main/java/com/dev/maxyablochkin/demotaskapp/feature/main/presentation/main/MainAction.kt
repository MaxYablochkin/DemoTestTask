package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

sealed interface MainAction {
    data class OnNavigateTabClick(val tab: NavigationTab) : MainAction
}