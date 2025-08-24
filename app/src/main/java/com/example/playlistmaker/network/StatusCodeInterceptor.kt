package com.example.playlistmaker.network

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

class StatusCodeException(code: Int) : Exception(code.toString())

class StatusCodeInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())

        when (response.code) {
            200 -> return response
            else -> throw StatusCodeException(response.code)
        }
    }
}