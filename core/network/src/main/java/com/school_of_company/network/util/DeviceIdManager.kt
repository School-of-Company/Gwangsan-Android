package com.school_of_company.network.util

import android.content.Context
import java.util.UUID
import androidx.core.content.edit

object DeviceIdManager {
    private const val PREF_NAME = "device_prefs"
    private const val KEY_DEVICE_ID = "device_id"

    fun getDeviceId(context: Context): UUID {
        // SharedPreferences 객체를 가져옴 (PREF_NAME은 SharedPreferences의 이름)
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedId = prefs.getString(KEY_DEVICE_ID, null)

        return if (savedId != null) {
            UUID.fromString(savedId)
        } else {
            val newId = UUID.randomUUID()
            prefs.edit() { putString(KEY_DEVICE_ID, newId.toString()) }
            newId
        }
    }
}
