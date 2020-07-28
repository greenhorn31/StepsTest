package com.example.stepstest.data

import com.example.stepstest.data.model.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StepsApi {

    @GET("comments")
    suspend fun comments(
        @Query(value = "_start") page: String,
        @Query(value = "_limit") limit: String
    ): Response<List<Comment>>
}
