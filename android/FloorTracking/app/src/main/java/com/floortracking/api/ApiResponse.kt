package com.floortracking.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T) : ApiResponse<T>()
    object EmptySuccess: ApiResponse<Nothing>()
    data class GenericError(val code: Int? = null, val error: Throwable? = null) :
        ApiResponse<Nothing>()
}

suspend fun <T> safeApiCallNotFlow(apiCall: suspend () -> T): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            ApiResponse.Success(response)
        } catch (e: KotlinNullPointerException) {
            ApiResponse.EmptySuccess
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            ApiResponse.GenericError(error = throwable)
        }
    }
}

fun <T> safeCall(apiCall: suspend () -> T): Flow<ApiResponse<T>> {
    return flow {
        try {
            val response = apiCall.invoke()
            emit(ApiResponse.Success(response))
        } catch (e: KotlinNullPointerException) {
            emit(ApiResponse.EmptySuccess)
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(ApiResponse.GenericError(code = e.code(), error = e))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.GenericError(error = e))
        }
    }
}