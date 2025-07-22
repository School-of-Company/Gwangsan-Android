package com.school_of_company.gwangsan_android.navigation

import com.school_of_company.chat.navigation.ChatRoute
import com.school_of_company.design_system.R
import com.school_of_company.inform.navigation.InformRoute
import com.school_of_company.main.navgation.MainStartRoute
import com.school_of_company.profile.navigation.MyProfileRoute

enum class TopLevelDestination(
    val unSelectedIcon: Int,
    val iconText: String,
    val routeName: String
) {
    MAIN(
        unSelectedIcon = R.drawable.home,
        iconText = "홈",
        routeName = MainStartRoute
    ),

    CHAT(
        unSelectedIcon = R.drawable.chat,
        iconText = "채팅",
        routeName = ChatRoute
    ),

    INFORM(
        unSelectedIcon = R.drawable.horn,
        iconText = "공지",
        routeName = InformRoute
    ),

    PROFILE(
        unSelectedIcon = R.drawable.person,
        iconText = "프로필",
        routeName = MyProfileRoute
    )
}
