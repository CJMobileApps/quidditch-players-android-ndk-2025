package com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HousesViewModelImpl @Inject constructor(
    private val quidditchPlayersUseCase: QuidditchPlayersUseCase,
    coroutineDispatchers: CoroutineDispatchers
) : ViewModel(), HousesViewModel {

    private val compositeJob = Job()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(tag)
            .e("coroutineExceptionHandler() error occurred: $throwable \n ${throwable.message}")
        snackbarState.value = HousesSnackbarState.ShowGenericError()
    }

    private val coroutineContext =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val coroutineContextDuckItTokenFlow =
        compositeJob + coroutineDispatchers.main + exceptionHandler + SupervisorJob()

    private val housesState = mutableStateOf<HousesState>(HousesState.LoadingState)

    private val snackbarState = mutableStateOf<HousesSnackbarState>(HousesSnackbarState.Idle)

    private val tag = HousesViewModelImpl::class.java.simpleName

    override fun getState() = housesState.value

    override fun getSnackbarState() = snackbarState.value

    init {
        viewModelScope.launch(coroutineContext) {

            quidditchPlayersUseCase
                .fetchHouses()
                .onSuccess {
                    housesState.value = HousesState.HousesLoadedState()
                }
                .onError { statusCode, error ->
                    //duckItListState.value = DuckItListState.DuckItListLoadedState()
                    //snackbarState.value = DuckItSnackbarState.UnableToGetDuckItListError()
                }

//            duckItUseCase.getPosts { posts ->
//                posts
//                    .onSuccess { postsData ->
//                        duckItListState.value = DuckItListState.DuckItListLoadedState(
//                            posts = postsData.posts.convertToStateObj()
//                        )
//
//                        viewModelScope.launch(coroutineContextDuckItTokenFlow) {
//                            accountUseCase.initDuckItTokenFlow(onIsUserLoggedIn = { isUserLoggedIn ->
//                                updateIsUserLoggedIn(isUserLoggedIn)
//                            })
//                        }
//                    }
//                    .onError { _ ->
//                        duckItListState.value = DuckItListState.DuckItListLoadedState()
//                        snackbarState.value = DuckItSnackbarState.UnableToGetDuckItListError()
//                    }
//            }
        }

    }

    sealed class HousesState {

        object LoadingState : HousesState()

        data class HousesLoadedState(
            val houses: List<House> = emptyList(),
            val housesNavRouteUi: MutableState<HousesNavRouteUi> = mutableStateOf(HousesNavRouteUi.Idle)
        ) : HousesState()
    }

    sealed class HousesSnackbarState {

        object Idle : HousesSnackbarState()

        data class ShowGenericError(
            val error: String? = null
        ) : HousesSnackbarState()
    }

    sealed class HousesNavRouteUi {

        object Idle : HousesNavRouteUi()
    }
}
