package com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.ui.NavItem
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModelImpl @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    savedStateHandle: SavedStateHandle,
    quidditchPlayersUseCase: QuidditchPlayersUseCase
) : ViewModel(), PlayersListViewModel {

    private val houseName: String = checkNotNull(savedStateHandle["houseName"])

    private val compositeJob = Job()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(tag)
            .e("coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message}")
        snackbarState.value = PlayersListSnackbarState.ShowGenericError()
    }

    private val coroutineContext =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val coroutineContextHousesFlow =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val playersListState = mutableStateOf<PlayersListState>(PlayersListState.LoadingState)

    private val snackbarState = mutableStateOf<PlayersListSnackbarState>(
        PlayersListSnackbarState.Idle
    )

    private val tag = PlayersListViewModelImpl::class.java.simpleName

    override fun getState() = playersListState.value

    override fun getSnackbarState() = snackbarState.value

    init {
        viewModelScope.launch(coroutineContext) {
            quidditchPlayersUseCase
                .fetchPlayersAndPositionsApis(houseName)
                .onError { _, _ ->
                    snackbarState.value = PlayersListSnackbarState.UnableToGetPlayersListError()
                }
        }

        viewModelScope.launch(coroutineContextHousesFlow) {
            quidditchPlayersUseCase.getAllPlayersToDB { playersResponse ->
                playersResponse
                    .onSuccess { players ->
                        playersListState.value = PlayersListState.PlayerListLoadedState(players)
                    }
                    .onError { _, _ ->
                        playersListState.value = PlayersListState.PlayerListLoadedState()
                        snackbarState.value = PlayersListSnackbarState.UnableToGetPlayersListError()
                    }
            }
        }
    }

    override fun resetSnackbarState() {
        snackbarState.value = PlayersListSnackbarState.Idle
    }

    override fun resetNavRouteUiToIdle() {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return
        state.playersNavRouteUi.value = PlayersListNavRouteUi.Idle
    }

    override fun goToPlayerDetailUi(playerId: UUID) {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return
        state.playersNavRouteUi.value =
            PlayersListNavRouteUi.GoToPlayerDetailUi(playerId.toString())
    }

    override fun getPlayersListNavRouteUiState(): PlayersListNavRouteUi {
        val state = getState()
        if (state !is PlayersListState.PlayerListLoadedState) return PlayersListNavRouteUi.Idle
        return state.playersNavRouteUi.value
    }

    sealed class PlayersListState {

        data object LoadingState : PlayersListState()

        data class PlayerListLoadedState(
            val players: List<PlayerEntity> = emptyList(),
            val playersNavRouteUi: MutableState<PlayersListNavRouteUi> = mutableStateOf(
                PlayersListNavRouteUi.Idle
            )
        ) : PlayersListState()
    }

    sealed class PlayersListSnackbarState {

        data object Idle : PlayersListSnackbarState()

        data class ShowGenericError(
            val error: String? = null
        ) : PlayersListSnackbarState()

        data class UnableToGetPlayersListError(
            val error: String? = null
        ) : PlayersListSnackbarState()
    }

    sealed class PlayersListNavRouteUi {

        data object Idle : PlayersListNavRouteUi()

        data class GoToPlayerDetailUi(val playerId: String) : PlayersListNavRouteUi() {

            fun getNavRouteWithArguments(): String =
                NavItem.PlayerDetail.getNavRouteWithArguments(playerId)
        }
    }
}
