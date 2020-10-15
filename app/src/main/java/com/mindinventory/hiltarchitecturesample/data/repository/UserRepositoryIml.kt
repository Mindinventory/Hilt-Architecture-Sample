package com.mindinventory.hiltarchitecturesample.data.repository

import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import com.mindinventory.hiltarchitecturesample.domain.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

// Constructor-injected, because Hilt needs to know how to
// provide instances of UserRepositoryIml, too.
class UserRepositoryIml @Inject constructor(private val userApi: UserApi) : UserRepository {

    override fun loadUsers(map: HashMap<String, String>): Single<ResponseUsers> {
        return userApi.loadUsers(map)
    }


}