package com.mindinventory.hiltarchitecturesample.data.repository

import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UserApi {

    @GET("api/")
    fun loadUsers(@QueryMap map: HashMap<String, String>) : Single<ResponseUsers>

}