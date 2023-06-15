package com.example.data.remote

import com.example.data.util.DataConstants.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", TOKEN)
            .build()
        return chain.proceed(request)
    }
}
