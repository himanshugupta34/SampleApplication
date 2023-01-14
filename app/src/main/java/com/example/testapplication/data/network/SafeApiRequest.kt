package com.example.testapplication.data.network

import com.example.testapplication.ApiException
import com.example.testapplication.util.Resource
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
                val errorMessage = jsonObject?.optString("responseMessage")
                val errorCode = response.code()
                val error = jsonObject?.optJSONArray("errors")
                var responseMessage = error?.let { "$errorMessage ${it.get(0)}".trim() } ?: run { errorMessage }
                if (responseMessage.isNullOrEmpty()) responseMessage = errorMsg

                throw ApiException(
                    message = responseMessage ?: "${response.message()} with error code $errorCode"
                )
            }
        }

    /**
     * will return the api response wrapped with Resource in Flow
     * although the preferred way is to wrap the Resource state in view model, so use above function ie. makeSafeRequestForFlow()
     *
     *  ApiException to be collected in view model
     */

    suspend fun <T : Any> makeSafeRequestForFlowResource(request: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            val response = request.invoke()
            if (response.isSuccessful)
                emit(Resource.Success(response.body()!!))
            else {
                val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                val error = jsonObject?.getString("responseMessage")
                val errorCode = response.code()

                throw ApiException(
                    message = error ?: "${response.message()} with error code $errorCode"
                )
            }
        }


    /**
     * will return the api response wrapped with in Resource State ie. Resource.Success.
     * No use of Flow in this function
     *
     *  ApiException to be collected in view model
     */
    suspend fun <T : Any> makeSafeRequestForResource(request: suspend () -> Response<T>): Resource<T> {
        val response = request.invoke()
        return if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Resource.Success(resultResponse)
            }!!
        } else {
            val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
            val error = jsonObject?.getString("responseMessage")
            val errorCode = response.code()

            throw ApiException(
                message = error ?: "${response.message()} with error code $errorCode"
            )
        }
    }

    /**
     *will simply return the response without wrapped in Flow or Resource State
     *
     *  ApiException to be collected in view model
     */
    suspend fun <T : Any> makeSafeRequest(request: suspend () -> Response<T>): T {
        val response = request.invoke()
        val b = response.body()
        if (response.isSuccessful) {
            return b!!
        } else {
            val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
            val error = jsonObject?.getString("responseMessage")
            val errorCode = response.code()

            throw ApiException(
                message = error ?: "${response.message()} with error code $errorCode"
            )
        }
    }

}