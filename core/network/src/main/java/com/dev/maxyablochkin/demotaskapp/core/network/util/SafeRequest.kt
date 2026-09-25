package com.dev.maxyablochkin.demotaskapp.core.network.util

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

abstract class SafeRequest {

    suspend fun <T : Any> safeRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            } else {
                val responseErr = response.errorBody()?.string()
                val message = StringBuilder()

                if (!responseErr.isNullOrEmpty()) {
                    try {
                        val json = JSONObject(responseErr)
                        when {
                            json.has("message") -> message.append(json.getString("message"))
                            json.has("error") -> message.append(json.getString("error"))
                        }
                    } catch (e: JSONException) {
                        message.append(responseErr)
                    }
                }

                if (message.isEmpty()) {
                    message.append("HTTP Error ${response.code()}: ${response.message()}")
                }

                Log.d("SAFE", "safe request error [${response.code()}]: $message")

                throw NetworkException.ApiException(
                    code = response.code(),
                    errorMessage = message.toString()
                )
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e("SAFE", "safe request exception", e)
            if (e is NetworkException) throw e
            throw NetworkException.UnknownException(e.message ?: "Unknown error")
        }
    }
}

sealed class NetworkException(message: String) : Exception(message) {
    data class ApiException(val code: Int, val errorMessage: String) : NetworkException(errorMessage)
    data class UnknownException(val errorMessage: String) : NetworkException(errorMessage)
}
