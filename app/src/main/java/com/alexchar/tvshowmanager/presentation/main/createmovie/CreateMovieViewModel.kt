package com.alexchar.tvshowmanager.presentation.main.createmovie

import androidx.lifecycle.*
import com.alexchar.tvshowmanager.MovieListQuery
import com.alexchar.tvshowmanager.domain.movie.usecase.ICreateMovieUseCase
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.presentation.util.immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMovieViewModel @Inject constructor(
    private val createMovieUseCase: ICreateMovieUseCase
) : ViewModel() {

    private val _requestState = MutableLiveData<ViewState<Unit>>()
    val requestState = _requestState.immutable()

    fun createMovie(title: String, releaseDate: String, seasons: Double?) {
        _requestState.postValue(ViewState.loading())

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = createMovieUseCase.execute(
                MovieListQuery.Node(
                    title = title,
                    releaseDate = releaseDate,
                    createdAt = System.currentTimeMillis(),
                    seasons = seasons
                )
            )) {
                is ViewState.Error -> {
                    _requestState.postValue(response.exception?.let { ViewState.error(it) })
                }
                is ViewState.Success -> {
                    _requestState.postValue(ViewState.success(Unit))
                }
            }

        }
    }
}