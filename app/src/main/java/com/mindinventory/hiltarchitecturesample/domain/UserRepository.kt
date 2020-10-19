package com.mindinventory.hiltarchitecturesample.domain

import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun loadUsers(map: HashMap<String, String>) : Single<ResponseUsers>

}