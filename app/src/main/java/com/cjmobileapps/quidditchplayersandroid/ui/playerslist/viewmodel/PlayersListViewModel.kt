package com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel

import java.util.UUID

interface PlayersListViewModel {

    fun getState(): PlayersListViewModelImpl.PlayersListState

    fun getSnackbarState(): PlayersListViewModelImpl.PlayersListSnackbarState

    fun resetSnackbarState()

    fun resetNavRouteUiToIdle()

    fun goToPlayerDetailUi(playerId: UUID)

    fun getPlayersListNavRouteUiState(): PlayersListViewModelImpl.PlayersListNavRouteUi
}
