package com.cjmobileapps.quidditchplayersandroid.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cjmobileapps.quidditchplayersandroid.ui.houses.HousesUi
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModelImpl
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavigationGraph(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = NavItem.Houses.navRoute) {
        composable(NavItem.Houses.navRoute) {
            val housesViewModel: HousesViewModel = hiltViewModel<HousesViewModelImpl>()

            HousesUi(
                navController = navController,
                housesViewModel = housesViewModel,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
        }
        composable(NavItem.PlayersList.navRoute) {
//            val newPostViewModel: NewPostViewModel = hiltViewModel<NewPostViewModelImpl>()
//
//            NewPostUi(
//                navController = navController,
//                newPostViewModel = newPostViewModel,
//                coroutineScope = coroutineScope,
//                snackbarHostState = snackbarHostState
//            )
        }
        composable(NavItem.PlayerDetail.navRoute) {
//            val logInViewModel: LogInViewModel = hiltViewModel<LogInViewModelImpl>()
//
//            LogInUi(
//                navController = navController,
//                logInViewModel = logInViewModel,
//                coroutineScope = coroutineScope,
//                snackbarHostState = snackbarHostState
//            )
        }
    }
}

sealed class NavItem(
    val navRoute: String
) {
    object Houses : NavItem(navRoute = "nav_houses")

    object PlayersList : NavItem(navRoute = "nav_players_list")

    object PlayerDetail : NavItem(navRoute = "nav_player_detail")
}
