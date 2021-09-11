package com.alexchar.tvshowmanager.presentation.main.createmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexchar.tvshowmanager.databinding.FragmentCreateMovieBinding
import com.alexchar.tvshowmanager.domain.util.ViewState
import com.alexchar.tvshowmanager.presentation.util.observe
import com.alexchar.tvshowmanager.presentation.util.skipNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateMovieFragment : Fragment() {

    private var _binding: FragmentCreateMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            observe(requestState, ::handleRequestState.skipNull())
        }

        setUpCreateMovieButton()
    }

    private fun handleRequestState(state: ViewState<Unit>) {
        when (state) {
            is ViewState.Loading -> {
                binding.swipeRefresh.isRefreshing = true
                binding.createButton.isEnabled = false
            }
            is ViewState.Success -> {
                binding.swipeRefresh.isRefreshing = false
                binding.createButton.isEnabled = true

                Toast.makeText(this@CreateMovieFragment.context, "New movie saved!", Toast.LENGTH_LONG).show()
                activity?.onBackPressed()
            }
            is ViewState.Error -> {
                binding.swipeRefresh.isRefreshing = false
                binding.createButton.isEnabled = true
                Toast.makeText(activity, state.exception, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setUpCreateMovieButton() {
        binding.createButton.setOnClickListener {
            val (title, releaseDate, seasons) = getInputs()

            viewModel.createMovie(title, releaseDate, seasons.toDoubleOrNull())
        }
    }

    private fun getInputs(): Triple<String, String, String> {
        return binding.run {
            Triple(nameEditText.text.toString(), dateEditText.text.toString(), seasonsEditText.text.toString())
        }
    }
}