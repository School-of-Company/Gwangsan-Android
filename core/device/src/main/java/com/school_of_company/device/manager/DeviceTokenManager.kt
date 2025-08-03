package com.school_of_company.device.manager

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.school_of_company.data.repository.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceTokenManager @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun saveDeviceToken(deviceToken: String) {
        localRepository.savedDeviceToken(deviceToken = deviceToken)
    }

    fun fetchDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                saveDeviceToken(deviceToken = task.result)
            }
        }
    }
}