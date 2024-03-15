package com.cjmobileapps.quidditchplayersandroid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cjmobileapps.quidditchplayersandroid.ui.theme.QuidditchPlayersAndroid2023Theme

@Composable
fun QuidditchPlayersUi() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    QuidditchPlayersAndroid2023Theme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(
                    navController = navController,
                    coroutineScope = coroutineScope,
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    }
}
