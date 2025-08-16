package com.school_of_company.gwangsan.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.school_of_company.device.manager.DeviceTokenManager
import com.school_of_company.device.manager.NotificationManager
import com.school_of_company.gwangsan.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GwangsanMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceTokenManager: DeviceTokenManager

    private val notificationManager: NotificationManager by lazy {
        NotificationManager(
            context = this,
            intentClass = MainActivity::class.java
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenManager.saveDeviceToken(deviceToken = token)
        }

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let { notification ->
            val title = notification.title ?: "새 알림"
            val body = notification.body ?: "내용 없음"

            notificationManager.sendNotification(title, body)
        }

        if (message.data.isNotEmpty()) {
            val title = message.data["title"] ?: "새 알림"
            val body = message.data["body"] ?: "내용 없음"

            notificationManager.sendNotification(title, body)
        }
    }
}