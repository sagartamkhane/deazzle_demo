package com.deazzle.demo.data.api

import com.deazzle.demo.data.model.UserResp
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/")
    suspend fun getUsers( @Query("results") result: String): UserResp

}