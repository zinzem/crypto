package com.example.crypto.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HttpClient @Inject constructor() {

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
        .build()

    companion object {
        private const val TIMEOUT_CONNECT: Long = 30000
        private const val TIMEOUT_READ: Long = 30000
        private const val TIMEOUT_WRITE: Long = 30000
    }
}

