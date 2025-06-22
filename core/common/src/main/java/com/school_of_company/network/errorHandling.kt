package com.school_of_company.network

import com.school_of_company.common.BadRequestException
import com.school_of_company.common.ConflictException
import com.school_of_company.common.NoInternetException
import com.school_of_company.common.OtherHttpException
import com.school_of_company.common.TimeOutException
import com.school_of_company.common.TokenExpirationException
import com.school_of_company.common.TooManyRequestException
import com.school_of_company.common.ForBiddenException
import com.school_of_company.common.UnauthorizedException
import com.school_of_company.common.ServerException
import com.school_of_company.common.NotFoundException
import com.school_of_company.common.NotAcceptableException

fun Throwable.errorHandling(
    badRequestAction: () -> Unit = {},
    unauthorizedAction: () -> Unit = {},
    forbiddenAction: () -> Unit = {},
    notFoundAction: () -> Unit = {},
    notAcceptableAction: () -> Unit = {},
    timeOutAction: () -> Unit = {},
    conflictAction: () -> Unit = {},
    tooManyRequestAction: () -> Unit = {},
    serverAction: () -> Unit = {},
    noInternetAction: () -> Unit = {},
    otherHttpAction: () -> Unit = {},
    unknownAction: () -> Unit = {},
) {
    when (this) {
        is BadRequestException -> badRequestAction()
        is UnauthorizedException, is TokenExpirationException -> unauthorizedAction()
        is ForBiddenException -> forbiddenAction()
        is NotFoundException -> notFoundAction()
        is NotAcceptableException -> notAcceptableAction()
        is TimeOutException -> timeOutAction()
        is ConflictException -> conflictAction()
        is TooManyRequestException -> tooManyRequestAction()
        is ServerException -> serverAction()
        is NoInternetException -> noInternetAction()
        is OtherHttpException -> otherHttpAction()
        else -> unknownAction()
    }
}