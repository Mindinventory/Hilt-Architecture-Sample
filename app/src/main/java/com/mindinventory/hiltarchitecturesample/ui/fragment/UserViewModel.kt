package com.mindinventory.hiltarchitecturesample.ui.fragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import com.mindinventory.hiltarchitecturesample.domain.UserRepository
import com.mindinventory.hiltarchitecturesample.ui.common.Resource
import com.mindinventory.hiltarchitecturesample.ui.common.Status
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

// Constructor-injected
class UserViewModel @ViewModelInject constructor
    (private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel(), LifecycleObserver {

    val usersEvent = MutableLiveData<Resource<ResponseUsers>>()

    private val compositeDisposable = CompositeDisposable()
    private fun Disposable.collect() = compositeDisposable.add(this)
    fun getUsers() {
        val mMap = HashMap<String, String>()
        mMap["results"] = "20"
        mMap["page"] = "1"
        userRepository.loadUsers(mMap)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                usersEvent.postValue(Resource(Status.LOADING))
            }
            .subscribe({
                usersEvent.postValue(Resource(Status.SUCCESS, it))
            }, {
                usersEvent.postValue(Resource(Status.ERROR, throwable = it))
            })
            .collect()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}