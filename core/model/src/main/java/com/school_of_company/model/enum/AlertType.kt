package com.school_of_company.model.enum

enum class AlertType(val value: String) {
    CHATTING_REQUEST("채팅 요청"),
    NOTICE("공지사항 관련"),
    TRADE_COMPLETE("거래 완료"),
    TRADE_COMPLETE_REJECT("거래 완료 거절"),
    OTHER_MEMBER_TRADE_COMPLETE("다른 사용자 거래 완료"),
    RECOMMENDER("추천인 관련"),
    REVIEW("리뷰 관련")
}