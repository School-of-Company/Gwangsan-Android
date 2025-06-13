package com.school_of_company.gwangsan_android.navigation

import com.school_of_company.design_system.R

enum class TopLevelDestination(
    val unSelectedIcon: Int,
    val iconText: String,
) {
    HOME(
        unSelectedIcon = R.drawable.home,
        iconText = "홈",
    ),

    POST(
        unSelectedIcon = R.drawable.copy,
        iconText = "게시글",
    ),

    CHAT(
        unSelectedIcon = R.drawable.chat,
        iconText = "채팅",
    ),

    NOTIFICATION(
        unSelectedIcon = R.drawable.horn,
        iconText = "공지",
    ),

    PROFILE(
        unSelectedIcon = R.drawable.person,
        iconText = "프로필",
    )
}
