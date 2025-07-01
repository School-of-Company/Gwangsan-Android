package com.school_of_company.network.util

import android.content.Context
import java.util.UUID
import androidx.core.content.edit

object DeviceIdManager {
    private const val PREF_NAME = "device_prefs"
    private const val KEY_DEVICE_ID = "device_id"

    /**
     * 기기의 고유 UUID를 가져옵니다.
     * - 기존에 저장된 UUID가 있으면 그것을 반환
     * - 없으면 새로 생성해서 SharedPreferences에 저장 후 반환
     */
    fun getDeviceId(context: Context): UUID {
        // SharedPreferences 객체 가져오기
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // 저장된 deviceId가 있는지 확인
        val savedId = prefs.getString(KEY_DEVICE_ID, null)

        return if (savedId != null) {
            // 저장된 ID가 있으면 해당 문자열을 UUID로 변환하여 반환
            UUID.fromString(savedId)
        } else {
            // 저장된 ID가 없으면 새로운 UUID 생성
            val newId = UUID.randomUUID()

            // 생성한 UUID를 SharedPreferences에 저장
            prefs.edit() { putString(KEY_DEVICE_ID, newId.toString()) }

            // 새로 생성한 UUID 반환
            newId
        }
    }
}
