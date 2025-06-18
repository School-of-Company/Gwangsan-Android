package com.school_of_company.network.util

import android.util.Log
import com.school_of_company.common.BadRequestException
import com.school_of_company.common.ConflictException
import com.school_of_company.common.ForBiddenException
import com.school_of_company.common.NoInternetException
import com.school_of_company.common.NotFoundException
import com.school_of_company.common.OtherHttpException
import com.school_of_company.common.ServerException
import com.school_of_company.common.TimeOutException
import com.school_of_company.common.TokenExpirationException
import com.school_of_company.common.TooManyRequestException
import com.school_of_company.common.UnKnownException
import com.school_of_company.common.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GwangsanApiHandler<T> {
    private lateinit var httpRequest: suspend () -> T

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    @Throws(TokenExpirationException::class)
    suspend fun sendRequest(): T {
        return try {
            Log.d("ApiHandler", "Success")
            withContext(Dispatchers.IO) {
                httpRequest.invoke()
            }
        } catch (e: HttpException) {
            val message = e.message
            Log.d("ApiHandler", message.toString())
            throw when (e.code()) {
                400 -> BadRequestException(
                    message = message
                )

                401 -> UnauthorizedException(
                    message = message,
                )

                403 -> ForBiddenException(
                    message = message,
                )

                404 -> NotFoundException(
                    message = message,
                )

                409 -> ConflictException(
                    message = message,
                )

                429 -> TooManyRequestException(
                    message = message
                )

                in 500..599 -> ServerException(
                    message = message,
                )

                else -> OtherHttpException(
                    message = message,
                    code = e.code()
                )
            }
        } catch (e: SocketTimeoutException) {
            throw TimeOutException(message = e.message)
        } catch (e: UnknownHostException) {
            throw NoInternetException()
        } catch (e: TokenExpirationException) {
            throw TokenExpirationException()
        } catch (e: Exception) {
            throw UnKnownException(message = e.message)
        }
    }
}