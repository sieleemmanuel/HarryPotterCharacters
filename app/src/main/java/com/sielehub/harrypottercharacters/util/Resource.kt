package com.sielehub.harrypottercharacters.util

sealed class Resource<T>(var data:T? = null, val message:String? = null){
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(message: String?) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
}
