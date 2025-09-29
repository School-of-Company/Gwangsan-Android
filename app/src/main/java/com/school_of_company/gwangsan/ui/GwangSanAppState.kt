package com.school_of_company.gwangsan.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.school_of_company.chat.navigation.navigateToChat
import com.school_of_company.gwangsan.navigation.TopLevelDestination
import com.school_of_company.inform.navigation.navigateToInform
import com.school_of_company.main.navgation.MainStartRoute
import com.school_of_company.main.navgation.navigateToMainStart
import com.school_of_company.post.navigation.navigateToPost
import com.school_of_company.profile.navigation.navigateToMyProfile
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberGwangSanAppState (
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) : GwangSanAppState {
    return remember(
        windowSizeClass,
        coroutineScope,
        navController
    ) {
        GwangSanAppState (
            windowSizeClass = windowSizeClass,
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class GwangSanAppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    val currentDestination: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route

    val topLevelDestinations: PersistentList<TopLevelDestination> = TopLevelDestination.topLevelDestinations

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                TopLevelDestination.MAIN -> navController.navigateToMainStart(topLevelNavOptions)
                TopLevelDestination.CHAT -> navController.navigateToChat(topLevelNavOptions)
                TopLevelDestination.INFORM -> navController.navigateToInform(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToMyProfile(topLevelNavOptions)
                TopLevelDestination.POST -> navController.navigateToPost(topLevelNavOptions)
            }
        }
    }
}

fun NavController.navigationPopUpToLogin(loginRoute: String) {
    this.navigate(loginRoute) {
        popUpTo(0) { inclusive = true }
    }
}

fun NavController.navigateToHomeAndClearLogin() {
    this.navigate(MainStartRoute) {
        popUpTo(0) { inclusive = true }
    }
}