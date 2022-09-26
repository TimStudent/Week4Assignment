package com.example.week4assignment.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignment.R
import com.example.week4assignment.databinding.SingleItemBinding
import com.example.week4assignment.model.room.MovieData
import com.squareup.picasso.Picasso
import java.lang.Exception


class MovieAdapter(
    private var movieList: MutableList<MovieData> = mutableListOf(),
    private val onMovieClickListener: (MovieData) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position], onMovieClickListener)

        with(holder){
            with(movieList[position]){
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun update(newData: List<MovieData>) {
        movieList.clear()
        movieList.addAll(newData)
        notifyDataSetChanged()
    }
}

class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieData, onMovieClickListener: (MovieData) -> Unit) {
        binding.categories.text = movie.title
        binding.categoriesHint.text = movie.overview
        binding.expandedView.visibility = if (movie.expand) View.VISIBLE else View.GONE
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w1280/" + movie.poster_path)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imageView)
        binding.cardLayout.setOnClickListener {
            movie.expand = !movie.expand
        }
        binding.play.setOnClickListener {
            Log.d(TAG, movie.id.toString())
            try{
                Navigation.createNavigateOnClickListener(
                    R.id.action_JokeFragment_to_WatchMovieFragment)
                    .onClick(binding.play)
            }
            catch (e: Exception){
                print(e.stackTrace)
            }
            try {
                Navigation.createNavigateOnClickListener(
                    R.id.action_UpcomingMoviesFragment_to_WatchMovieFragment
                ).onClick(binding.play)
            }
            catch (e: Exception){
                print(e.stackTrace)
            }
            try {
                Navigation.createNavigateOnClickListener(
                    R.id.action_PopularMoviesFragment_to_WatchMovieFragment
                ).onClick(binding.play)
            }
            catch (e: Exception){
                print(e.stackTrace)
            }
            onMovieClickListener.invoke(movie)
        }
    }
}