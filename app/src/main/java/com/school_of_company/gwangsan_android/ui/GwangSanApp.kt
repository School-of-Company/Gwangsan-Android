package com.school_of_company.gwangsan_android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.school_of_company.gwangsan_android.navigation.GwangsanNavHost
import com.school_of_company.gwangsan_android.navigation.TopLevelDestination
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBar
import com.school_of_company.design_system.componet.bottombar.GwangSanNavigationBarItem
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.main.navgation.MainRoute
import com.school_of_company.main.navgation.MainStartRoute
import com.school_of_company.profile.navigation.MyProfileRoute
import com.school_of_company.profile.navigation.MyReviewRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GwangSanApp(
    startDestination: String,
    windowSizeClass: WindowSizeClass,
    appState: GwangSanAppState = rememberGwangSanAppState(windowSizeClass = windowSizeClass)
) {
    val isBottomBarVisible = remember { mutableStateOf(true) }

    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    // 여기에 보여주고 싶은 탑 레벨 목적지만 넣으세요.
    val topLevelDestinationRoute = listOf(
        MainStartRoute,
        MainRoute,
        MyProfileRoute
        // 필요 시 여기에 추가
    )

    // 현재 화면의 라우트와 topLevelDestination을 비교해서 BottomBar 표시 여부 결정
    navBackStackEntry?.destination?.route?.let {
        isBottomBarVisible.value = topLevelDestinationRoute.contains(it)
    }

    GwangSanTheme { colors, _ ->
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = colors.white,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (isBottomBarVisible.value) {
                    GwangSanBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination
                    )
                }
            }
        ) { _ ->
            GwangsanNavHost(
                appState = appState,
                startDestination = startDestination
            )
        }
    }
}

@Composable
fun GwangSanBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    GwangSanTheme { _, typography ->
        GwangSanNavigationBar {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

                GwangSanNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.unSelectedIcon),
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = destination.unSelectedIcon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = destination.iconText,
                            style = typography.titleSmall
                        )
                    }
                )
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, ignoreCase = true) ?: false
    } ?: false