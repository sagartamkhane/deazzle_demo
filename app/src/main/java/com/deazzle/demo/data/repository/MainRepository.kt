package com.deazzle.demo.data.repository

import com.deazzle.demo.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}