package com.alexchar.tvshowmanager.domain.movie.model

data class Movie(
    var id: Long,
    val title: String,
    val releaseDate: String?,
    val seasons: Double?
)