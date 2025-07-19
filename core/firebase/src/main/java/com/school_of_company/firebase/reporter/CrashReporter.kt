package com.school_of_company.firebase.reporter

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.school_of_company.firebase.BuildConfig
import com.school_of_company.network.dto.webhook.info.CrashInfo
import com.school_of_company.network.dto.webhook.message.DiscordEmbed
import com.school_of_company.network.dto.webhook.message.DiscordField
import com.school_of_company.network.dto.webhook.message.DiscordFooter
import com.school_of_company.network.dto.webhook.message.DiscordMessage
import com.school_of_company.network.api.WebhookAPI
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CrashReporter @Inject constructor(
    private val webhookAPI: WebhookAPI
) {

    fun setupGlobalExceptionHandler(context: Context) {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            try {
                Log.e("CrashReporter", "앱 크래시 발생", exception)

                FirebaseCrashlytics.getInstance().recordException(exception)

                try {
                    runBlocking {
                        sendDiscordWebhook(exception, context)
                        Log.d("CrashReporter", "크래시 웹훅 전송 성공")

                    }
                } catch (e: Exception) {
                    Log.e("CrashReporter", "크래시 웹훅 전송 실패", e)
                }

            } catch (e: Exception) {
                Log.e("CrashReporter", "크래시 핸들러에서 오류 발생", e)
            } finally {
                defaultHandler?.uncaughtException(thread, exception)
            }
        }
    }

    private suspend fun sendDiscordWebhook(exception: Throwable, context: Context) {
        val crashInfo = buildCrashInfo(exception, context)
        sendDiscordWebhookWithCrashInfo(crashInfo)
    }

    private suspend fun sendDiscordWebhookWithCrashInfo(crashInfo: CrashInfo) {

        val embed = DiscordEmbed(
            title = "앱 크래시 발생함...",
            description = "**${crashInfo.exceptionType}**: ${crashInfo.exceptionMessage ?: "알 수 없는 오류"}",
            color = 0xFF0000,
            fields = listOf(
                DiscordField("앱 버전", crashInfo.appVersion, true),
                DiscordField("안드로이드 버전", crashInfo.androidVersion, true),
                DiscordField("기기 모델", crashInfo.deviceModel, true),
                DiscordField("발생 시간", crashInfo.timestamp, true),
                DiscordField("스택 트레이스", crashInfo.stackTrace, false),
                DiscordField("확인해줘...", BuildConfig.DISCORD_MEMBER_ID, false),
                DiscordField(
                    "Firebase Crashlytics",
                    "[Crashlytics에서 보기](${BuildConfig.FIREBASE_CRASHLYTICS_ISSUE_URL})",
                    false
                )
            ),
            timestamp = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            ).format(Date()),
            footer = DiscordFooter("Firebase Crashlytics")
        )

        val message = DiscordMessage(embeds = listOf(embed))

        webhookAPI.sendDiscordMessage(url = BuildConfig.DISCORD_WEBHOOK_URL, body = message)
        Log.d("CrashReporter", "Discord 웹훅 전송 성공")
    }

    private fun buildCrashInfo(exception: Throwable, context: Context): CrashInfo {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val stackTrace = exception.stackTraceToString()

        return CrashInfo(
            appVersion = "${packageInfo.versionName} (${packageInfo.versionCode})",
            androidVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
            deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            stackTrace = stackTrace.take(1000) + if (stackTrace.length > 1000) "..." else "",
            exceptionType = exception.javaClass.simpleName,
            exceptionMessage = exception.message
        )
    }
}