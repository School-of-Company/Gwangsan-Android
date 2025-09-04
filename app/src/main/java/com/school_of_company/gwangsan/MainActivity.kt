package com.school_of_company.gwangsan

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.viewModels
import com.school_of_company.main.navgation.MainRoute
import com.school_of_company.signin.navigation.StartRoute
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.school_of_company.gwangsan.ui.GwangSanApp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.device.manager.DeviceTokenManager
import com.school_of_company.main.navgation.MainStartRoute
import com.school_of_company.signin.navigation.StartRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermission()

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.appLoginState.value is AppLoginState.Loading
        }

        enableEdgeToEdge()

        setContent {
            if (viewModel.appLoginState.value is AppLoginState.Loading) return@setContent
            val startDestination = when (viewModel.appLoginState.value) {
                is AppLoginState.Success -> MainStartRoute// 로그인 성공시 홈으로
                else ->  StartRoute// 그 외에는 로그인 화면으로
            }
            CompositionLocalProvider {
                GwangSanTheme { _, _ ->
                    GwangSanApp(
                        startDestination = startDestination,
                        windowSizeClass = calculateWindowSizeClass(activity = this))
                }
            }
        }

        lifecycleScope.launch {
            deviceTokenManager.fetchDeviceToken()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!isGranted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}