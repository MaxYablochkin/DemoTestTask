package com.dev.maxyablochkin.demotaskapp.core.network.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val urlWithKey = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", NetworkConstants.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithKey)
            .build()

        return chain.proceed(newRequest)
    }
}