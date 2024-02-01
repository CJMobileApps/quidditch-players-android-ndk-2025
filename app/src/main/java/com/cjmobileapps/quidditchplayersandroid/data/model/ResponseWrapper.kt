package com.cjmobileapps.quidditchplayersandroid.data.model

import kotlinx.coroutines.Deferred
import retrofit2.Response

data class ResponseWrapper<T>(
    val data: T? = null,
    val error: Error? = null,
    val statusCode: Int? = null
)

data class Error(
    val isError: Boolean = false,
    val message: String? = null
)

data class ResponseApiWrapper<T>(
    val responseWrapper: Response<ResponseWrapper<T>>,
)

data class DeferredResponseWrapper<T>(
    val deferredWrapper: Deferred<ResponseApiWrapper<T>>
)
