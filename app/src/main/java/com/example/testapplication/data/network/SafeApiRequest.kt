package com.example.testapplication.data.network

import com.example.testapplication.ApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    /**
     * will return the api response wrapped with in coroutine Flow
     * Resource wrapping will now be done in view model.
     * This is the most recommended way of handling the response and catching the exceptions while using flow.
     *
     * ApiException to be collected in view model
     */
    suspend fun <T : Any> makeSafeRequestForFlow(request: suspend () -> Response<T>): Flow<T> =
        flow {
            val response = request.invoke()
            if (response.isSuccessful)
                emit(response.body()!!)
            else {
                val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                val errorMsg = jsonObject?.optString("message")
                val errorCode = response.code()

                throw ApiException(
                    message = errorMsg ?: "${response.message()} with error code $errorCode"
                )
            }
        }
}