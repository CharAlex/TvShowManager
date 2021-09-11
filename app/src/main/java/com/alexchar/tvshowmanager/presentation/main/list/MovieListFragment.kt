package com.alexchar.tvshowmanager.presentation.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexchar.tvshowmanager.databinding.FragmentMovieListBinding
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.presentation.util.observe
import com.alexchar.tvshowmanager.presentation.util.skipNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private var movieAdapter: MovieListAdapter? = null

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        binding.swipeRefresh.isEnabled = false

        binding.moviesRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MovieListFragment.context)

            movieAdapter = MovieListAdapter()

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (
                        lastPosition == movieAdapter?.itemCount?.minus(1) && !viewModel.state.isLoading && !viewModel.state.isResultExhausted
                    ) {
                        viewModel.syncNextMoviesPage()
                    }
                }
            })
            adapter = movieAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.run {
            observe(movies, ::handleMovies.skipNull())
            observe(requestState, ::handleStateChange.skipNull())
        }
    }
    private fun handleMovies(list: List<Movie>) {
        movieAdapter?.submitList(list)
    }

    private fun handleStateChange(state: ViewState<Unit>) {
        when (state) {
            is ViewState.Loading -> {
                binding.swipeRefresh.isRefreshing = true
            }
            is ViewState.Success -> {
                binding.swipeRefresh.isRefreshing = false
            }
            is ViewState.Error -> {
                binding.swipeRefresh.isRefreshing = true
                Toast.makeText(activity, state.exception, Toast.LENGTH_LONG).show()
            }
        }
    }
}