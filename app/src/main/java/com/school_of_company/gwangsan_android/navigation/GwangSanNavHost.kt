package com.school_of_company.gwangsan_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.school_of_company.gwangsan_android.ui.GwangSanAppState


@Composable
fun GwangsanNavHost(
    modifier: Modifier = Modifier,
    appState: GwangSanAppState,
    startDestination: String = ""
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

    }
}