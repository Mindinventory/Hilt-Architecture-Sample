package com.mindinventory.hiltarchitecturesample.data.entity

import com.google.gson.annotations.SerializedName
import com.mindinventory.hiltarchitecturesample.data.common.ResponseBase

class ResponseUsers : ResponseBase() {

    @SerializedName("results")
    val users = ArrayList<User>()
}

open class User{
    var email : String = ""
    var name : Name? = null
    var picture : Picture? = null
}

data class Name(val title : String, val first: String, val last: String)
{
    fun getFullName() : String
    {
        return "$title $first $last"
    }
}
data class Picture(val thumbnail : String)