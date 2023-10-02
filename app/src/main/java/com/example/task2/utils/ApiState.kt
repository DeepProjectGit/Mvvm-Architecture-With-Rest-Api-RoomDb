package com.example.task2.utils

sealed class ApiState<out T>{
    data class Success<out T>(val data:T):ApiState<T>()
    data class Failure(val isNetworkError:Boolean,val msg:Throwable):ApiState<Nothing>()
    object Loading:ApiState<Nothing>()
    object Empty:ApiState<Nothing>()
}
