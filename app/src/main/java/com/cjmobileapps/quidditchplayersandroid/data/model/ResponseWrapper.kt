package com.cjmobileapps.quidditchplayersandroid.data.model

import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.HttpURLConnection

data class ResponseWrapper<T>(
    val data: T? = null,
    val error: Error? = null,
    val statusCode: Int = 999,
)

data class Error(
    val isError: Boolean = false,
    val message: String? = null,
)

data class ResponseApiWrapper<T>(
    val responseWrapper: Response<ResponseWrapper<T>>,
)

data class DeferredResponseWrapper<T>(
    val deferredWrapper: Deferred<ResponseApiWrapper<T>>,
)

data class ResponseWrappers<T1, T2>(
    val responseWrapper1: ResponseWrapper<T1>,
    val responseWrapper2: ResponseWrapper<T2>,
)

object ResponseWrapperUtil {
    fun <T> createResponseWrapperSuccess(data: T) = ResponseWrapper(data = data, statusCode = HttpURLConnection.HTTP_OK)

    fun <T> createResponseWrapperError(
        error: Error,
        statusCode: Int = HttpURLConnection.HTTP_BAD_REQUEST,
    ) = ResponseWrapper<T>(error = error, statusCode = statusCode)
}
