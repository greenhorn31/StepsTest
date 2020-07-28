package com.example.stepstest.data

import com.example.stepstest.ui.viewObject.Resource
import com.example.stepstest.ui.viewObject.Status
import retrofit2.Response

fun <T> Response<T>.formResource(): Resource<T> {
    return try {
        if (this.isSuccessful) {
            Resource(
                Status.SUCCESS,
                this.body(),
                null
            )
        } else {
            Resource.error("Server Error")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}