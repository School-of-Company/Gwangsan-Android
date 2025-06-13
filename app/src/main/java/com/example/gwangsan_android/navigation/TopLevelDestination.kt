package com.example.gwangsan_android.navigation

import com.school_of_company.gwangsan_android.R

enum class TopLevelDestination(
    val unSelectedIcon: Int,
    val iconText: String,
) {
    HOME(
        unSelectedIcon = com.school_of_company.design_system.R.drawable.home,
        iconText = "홈",
    ),

    POST(
        unSelectedIcon = com.school_of_company.design_system.R.drawable.copy,
        iconText = "게시글",
    ),

    CHAT(
        unSelectedIcon = com.school_of_company.design_system.R.drawable.chat,
        iconText = "채팅",
    ),

    NOTIFICATION(
        unSelectedIcon = com.school_of_company.design_system.R.drawable.horn,
        iconText = "공지",
    ),

    PROFILE(
        unSelectedIcon = com.school_of_company.design_system.R.drawable.person,
        iconText = "프로필",
    )
}
