package com.cjmobileapps.quidditchplayersandroid.ui.houses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.cjmobileapps.quidditchplayersandroid.R
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModelImpl
import kotlinx.coroutines.CoroutineScope

@Composable
fun HousesUi(
    navController: NavController,
    housesViewModel: HousesViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = { },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box {
            when (val state = housesViewModel.getState()) {
                is HousesViewModelImpl.HousesState.LoadingState -> {

                }

                is HousesViewModelImpl.HousesState.HousesLoadedState -> {
                    HousesLoadedUi(
                        modifier = Modifier.padding(innerPadding),
//                        duckItListLoadedState = state,
//                        duckItListViewModel = duckItListViewModel,
                        navController = navController,
                        //pullToRefreshState = pullToRefreshState
                    )
                }
            }
        }

        val snackbarMessage: String? = when (val state = housesViewModel.getSnackbarState()) {
            is HousesViewModelImpl.HousesSnackbarState.Idle -> null
            is HousesViewModelImpl.HousesSnackbarState.ShowGenericError -> state.error
                ?: stringResource(R.string.some_error_occurred)
        }

        if (snackbarMessage != null) {
//            DuckItListSnackbar(
//                message = snackbarMessage,
//                coroutineScope = coroutineScope,
//                snackbarHostState = snackbarHostState,
//                duckItListViewModel = duckItListViewModel
//            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HousesLoadedUi(
    modifier: Modifier,
//    duckItListLoadedState: DuckItListLoadedState,
//    duckItListViewModel: DuckItListViewModel,
    navController: NavController
) {
    //val posts = duckItListLoadedState.posts

//    DuckItListUi(
//        modifier = modifier,
//        posts = posts,
//        lastIndex = duckItListLoadedState.posts.lastIndex,
//        onUpvoteButtonClicked = { duckItListViewModel.upvote(postId = it) },
//        onDownvoteButtonClicked = { duckItListViewModel.downvote(postId = it) }
//    )

//    when (duckItListViewModel.getDuckItListNavRouteUiState()) {
//        is DuckItListViewModelImpl.DuckItListNavRouteUi.Idle -> {}
//        is DuckItListViewModelImpl.DuckItListNavRouteUi.GoToLogInScreenUi -> {
//            navController.navigate(NavItem.LogIn.navRoute)
//            duckItListViewModel.resetNavRouteUiToIdle()
//        }
//    }
}
