package com.cjmobileapps.quidditchplayersandroid.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cjmobileapps.quidditchplayersandroid.ui.houses.HousesUi
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.houses.viewmodel.HousesViewModelImpl
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.PlayerUi
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayerListViewModel
import com.cjmobileapps.quidditchplayersandroid.ui.playerslist.viewmodel.PlayerListViewModelImpl
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
        composable(
            NavItem.PlayersList.navRoute,
            arguments = NavItem.PlayersList.arguments
        ) {
            val playerListViewModel: PlayerListViewModel = hiltViewModel<PlayerListViewModelImpl>()

            PlayerUi()
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
    val navRoute: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Houses : NavItem(navRoute = "nav_houses")

    data object PlayersList : NavItem(
        navRoute = "nav_players_list/{houseName}",
        arguments = listOf(
            navArgument("houseName") { type = NavType.StringType }
        )
    ) {

        fun getNavRouteWithArguments(houseName: String): String {
            return "nav_players_list/$houseName"
        }
    }

    data object PlayerDetail : NavItem(navRoute = "nav_player_detail")
}
