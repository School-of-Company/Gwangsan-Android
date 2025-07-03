package com.school_of_company.device.manager

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.school_of_company.design_system.R

private object Notifications {
    const val CHANNEL_ID = "gwangsan_id"
    const val CHANNEL_NAME = "gwangsan_name"
    const val CHANNEL_DESCRIPTION = "gwangsan_description"
}

class NotificationManager(
    private val context: Context,
    private val intentClass: Class<*>
) {
    init {
        createNotificationChannel()
    }

    private val notificationManagerCompat: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(
        title: String?,
        content: String?,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        if (!notificationPermissionGranted(context)) return

        val intent = Intent(context, intentClass).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val contentPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, Notifications.CHANNEL_ID)
            .setSmallIcon(R.drawable.gwangsan)
            .setColor(ContextCompat.getColor(context, R.color.main))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(contentPendingIntent)
            .apply {
                title?.let { setContentTitle(it) }
                content?.let { setContentText(it) }
            }

        notificationManagerCompat.notify(notificationId, builder.build())
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =
            NotificationChannel(
                Notifications.CHANNEL_ID,
                Notifications.CHANNEL_NAME,
                importance
            ).apply {
                this.description = Notifications.CHANNEL_DESCRIPTION
            }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun notificationPermissionGranted(context: Context): Boolean {
        return (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS,
                        ) == PackageManager.PERMISSION_GRANTED
                ) || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
    }
}
