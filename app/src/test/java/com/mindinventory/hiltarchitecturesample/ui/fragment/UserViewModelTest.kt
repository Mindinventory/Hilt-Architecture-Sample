package com.mindinventory.hiltarchitecturesample.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import com.mindinventory.hiltarchitecturesample.data.entity.User
import com.mindinventory.hiltarchitecturesample.data.entity.randomUserResponse
import com.mindinventory.hiltarchitecturesample.domain.UserRepository
import com.mindinventory.hiltarchitecturesample.ui.common.Resource
import com.mindinventory.hiltarchitecturesample.ui.common.RxImmediateSchedulerRule
import com.mindinventory.hiltarchitecturesample.ui.common.Status
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    //input value
    private val results: String = "20"
    private val page: String = "1"
    private val hashMap = HashMap<String, String>().apply {
        put("results", results)
        put("page", page)
    }

    //output value
    private val successLoginResponse =
        Resource(Status.SUCCESS, randomUserResponse().users)
    private val errorLoginResponse: Resource<Throwable> =
        Resource(Status.ERROR, throwable = Exception())
    private val loadingLoginResponse: Resource<ResponseUsers> =
        Resource(Status.LOADING)

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    //mock dependencies
    private val userRepository = mock<UserRepository>()
    private val savedStateHandle = mock<SavedStateHandle>()
    private val randomUserViewModel by lazy { UserViewModel(userRepository, savedStateHandle) }
    private var randomUserObserver = mock<Observer<Resource<ArrayList<User>>>>()

    @Captor
    var argumentCaptor: ArgumentCaptor<Resource<ArrayList<User>>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        reset(userRepository)
    }

    @Test
    fun fetchRandomUsersTestSuccess() {

        val delayer = PublishSubject.create<Resource<ArrayList<User>>>()

        stubFetchRandomUserSuccess(delayer)

        randomUserViewModel.usersEvent.observeForever(randomUserObserver)
        randomUserViewModel.getUsers()
        argumentCaptor?.apply {
            Mockito.verify(randomUserObserver).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(loadingLoginResponse))
            )
        }
        delayer.onComplete()

        argumentCaptor?.apply {
            Mockito.verify(randomUserObserver, Mockito.times(2)).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(successLoginResponse))
            )
        }
    }

    @Test
    fun fetchRandomUsersTestError() {
        stubFetchRandomUserError()

        randomUserViewModel.usersEvent.observeForever(randomUserObserver)
        randomUserViewModel.getUsers()
        argumentCaptor?.apply {
            Mockito.verify(randomUserObserver, Mockito.times(2)).onChanged(capture())

            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(errorLoginResponse))
            )
        }
    }

    //----------------------------stubbing-------------------------------------//
    private fun stubFetchRandomUserSuccess(delayer: PublishSubject<Resource<ArrayList<User>>>) {
        whenever(
            userRepository.loadUsers(hashMap)
        ).thenReturn(
            Single.create<ResponseUsers> { emitter ->
                try {
                    emitter.onSuccess(randomUserResponse())
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }.delaySubscription(delayer)
        )
    }

    private fun stubFetchRandomUserError() {
        whenever(
            userRepository.loadUsers(hashMap)
        ).thenReturn(
            Single.error(Throwable())
        )
    }
}