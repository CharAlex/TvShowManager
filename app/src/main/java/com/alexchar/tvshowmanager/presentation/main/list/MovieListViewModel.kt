package com.alexchar.tvshowmanager.presentation.main.list

import androidx.lifecycle.*
import com.alexchar.tvshowmanager.domain.movie.usecase.IGetMoviesUseCase
import com.alexchar.tvshowmanager.domain.movie.MovieDataState
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.movie.usecase.ISyncMoviesUseCase
import com.alexchar.tvshowmanager.domain.util.Constants
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.presentation.util.immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getMoviesUseCase: IGetMoviesUseCase,
    private val syncMoviesUseCase: ISyncMoviesUseCase
) : ViewModel() {

    val state = MovieDataState()

    private val _requestState = MutableLiveData<ViewState<Unit>>()
    val requestState = _requestState.immutable()

    var movies: LiveData<List<Movie>> = getMoviesUseCase.execute(
    ).asLiveData()

    init {
        syncNextMoviesPage()
    }

    fun syncNextMoviesPage() {
        incrementPageNumber()
        _requestState.postValue(ViewState.loading())

        viewModelScope.launch(Dispatchers.IO) {

            when (val response = syncMoviesUseCase.execute(state.first, state.skip)) {
                is ViewState.Error -> {
                    _requestState.postValue(response.exception?.let { ViewState.error(it) })
                }
                is ViewState.Success -> {
                    _requestState.postValue(ViewState.success(Unit))
                }
            }

        }
    }

    private fun incrementPageNumber() {
        state.skip += state.first
        state.first += Constants.PAGINATION_PAGE_SIZE
    }
}