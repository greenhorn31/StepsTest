package com.example.stepstest.data.interceptors

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.HashMap
import java.util.LinkedHashMap

private const val HEADER_NAME_CONTENT_TYPE = "Content-Type"
private const val CONTENT_TYPE_JSON = "application/json"

class HeaderInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = getHeadersForRequest(chain.request())
        val request = chain.request().newBuilder().headers(headers).build()
        return chain.proceed(request)
    }

    private fun getHeadersForRequest(request: Request): Headers {
        return Headers.of(apiHeaders(headersToMap(request.headers())))
    }

    private fun apiHeaders(existingHeaders: Map<String, String>): Map<String, String> {
        val headers = HashMap(existingHeaders)
        headers[HEADER_NAME_CONTENT_TYPE] = CONTENT_TYPE_JSON
        return headers
    }

    private fun headersToMap(headers: Headers): Map<String, String> {
        val headersMap = LinkedHashMap<String, String>()
        for (i in 0 until headers.size()) {
            headersMap[headers.name(i)] = headers.value(i)
        }
        return headersMap
    }
}