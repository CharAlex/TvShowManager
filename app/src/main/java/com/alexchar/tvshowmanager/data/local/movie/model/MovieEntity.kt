package com.alexchar.tvshowmanager.data.local.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.toDateString

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String?,

    @ColumnInfo(name = "created_at")
    val createdAtDate: String?,

    @ColumnInfo(name = "seasons")
    val seasons: Double?
)

fun MovieEntity.toDomain() = Movie(
    id, title, releaseDate, seasons
)

fun MovieListQuery.Node.toEntity() = MovieEntity(
    title = title, releaseDate = releaseDate.toDateString(), createdAtDate = createdAt.toDateString(), seasons = seasons
)