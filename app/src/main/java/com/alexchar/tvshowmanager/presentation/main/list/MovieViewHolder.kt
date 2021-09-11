package com.alexchar.tvshowmanager.presentation.main.list

import androidx.recyclerview.widget.RecyclerView
import com.alexchar.tvshowmanager.R
import com.alexchar.tvshowmanager.databinding.ItemMovieBinding
import com.alexchar.tvshowmanager.domain.movie.model.Movie
import com.alexchar.tvshowmanager.presentation.util.toLocalDate

class MovieViewHolder
constructor(
    private val binding: ItemMovieBinding,
    private val interaction: MovieListAdapter.MovieListItemInteraction? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie) {
        binding.root.setOnClickListener {
            interaction?.onItemSelected(adapterPosition, item)
        }

        binding.titleText.text = item.title
        binding.releaseDateText.text = item.releaseDate?.toLocalDate()
        binding.seasonsText.text = binding.root.context.getString(R.string.seasons, item.seasons.toString())

    }
}