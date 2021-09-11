package com.alexchar.tvshowmanager.domain.util

sealed class ViewState<T>{
    class Loading<T> : ViewState<T>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error<T>(val exception: String? = "Generic error") : ViewState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}