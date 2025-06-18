package com.school_of_company.network.util

import android.util.Log
import com.kim.common.BadRequestException
import com.kim.common.ConflictException
import com.kim.common.ForBiddenException
import com.kim.common.NoInternetException
import com.kim.common.NotFoundException
import com.kim.common.OtherHttpException
import com.kim.common.ServerException
import com.kim.common.TimeOutException
import com.kim.common.TokenExpirationException
import com.kim.common.TooManyRequestException
import com.kim.common.UnKnownException
import com.kim.common.UnauthorizedException
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