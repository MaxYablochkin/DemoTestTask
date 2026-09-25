package com.dev.maxyablochkin.demotaskapp.feature.main.presentation.main

import com.dev.maxyablochkin.demotaskapp.core.presentation.BaseViewModel

class MainViewModel : BaseViewModel<MainState, MainAction, MainNavEvent, Nothing>(
    initialState = MainState()
) {
    override fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnNavigateTabClick -> onNavigateTabClick(action.tab)
        }
    }

    private fun onNavigateTabClick(tab: NavigationTab) {
        updateState { copy(selectedTab = tab) }
        when (tab) {
            NavigationTab.HOME -> launchNavEvent(MainNavEvent.Home)
            NavigationTab.CATEGORIES -> launchNavEvent(MainNavEvent.Categories)
        }
    }
}