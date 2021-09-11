package com.alexchar.tvshowmanager.domain.movie

data class MovieDataState(
    val isLoading: Boolean = false,
    var query: String = "",
    var first: Int = 0,
    var skip: Int = 0,
    val isResultExhausted: Boolean = false,
    val errorMessage: String? = null
)