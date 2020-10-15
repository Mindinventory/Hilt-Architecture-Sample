package com.mindinventory.hiltarchitecturesample.ui.common

data class Resource<out T> constructor(
    val status: Status,
    val data: T? = null,
    val throwable: Throwable? = null
){
    fun isLoading() = status == Status.LOADING
}
