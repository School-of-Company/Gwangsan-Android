package com.school_of_company.gwangsan_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.lifecycleScope
import com.school_of_company.gwangsan_android.ui.GwangSanApp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.device.manager.DeviceTokenManager
import com.school_of_company.signin.navigation.StartRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val startDestination = StartRoute
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
}