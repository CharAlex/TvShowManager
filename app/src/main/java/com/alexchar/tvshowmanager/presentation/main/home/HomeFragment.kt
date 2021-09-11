package com.alexchar.tvshowmanager.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.alexchar.tvshowmanager.databinding.FragmentHomeBinding
import com.alexchar.tvshowmanager.domain.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coverImage.load(Constants.COVER_IMAGE_URL) {
            crossfade(true)
        }

        binding.newShowButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewShowFragment()
            findNavController().navigate(action)
        }

        binding.showListButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToShowListFragment()
            findNavController().navigate(action)
        }
    }
}