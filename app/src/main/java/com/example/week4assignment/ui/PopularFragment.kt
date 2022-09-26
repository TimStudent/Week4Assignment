package com.example.week4assignment.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4assignment.adapter.MovieAdapter
import com.example.week4assignment.databinding.FragmentPopularBinding
import com.example.week4assignment.model.MovieViewModel
import com.example.week4assignment.utils.UIState


class PopularFragment : Fragment() {

    private val binding by lazy {
        FragmentPopularBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        MovieAdapter{
            mMovieViewModel.movieID = it.id
            mMovieViewModel.movieTitle = it.title
            mMovieViewModel.movieOverView = it.overview
            mMovieViewModel.movieReleaseDate = it.release_date
            mMovieViewModel.moviePoster = it.poster_path
        }
    }
    private lateinit var mMovieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mMovieViewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.loading.visibility = View.INVISIBLE
        mMovieViewModel.status.observe(viewLifecycleOwner) {state->
            when(state) {
                is UIState.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.eventRecycler.visibility = View.GONE
                }
                is UIState.SUCCESS  -> {
                    binding.loading.visibility = View.GONE
                    binding.eventRecycler.visibility = View.VISIBLE
                    state.success?.let { adapter.update(it) }
                }
                is UIState.SUCCESS2  -> {
                    binding.loading.visibility = View.GONE
                    binding.eventRecycler.visibility = View.VISIBLE
                    state.success
                }
                is UIState.ERROR -> {
                    binding.loading.visibility = View.GONE
                    binding.eventRecycler.visibility = View.GONE
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error Loading Music")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry") { dialog, _ ->
                            mMovieViewModel.getPopularData()
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
        }
        mMovieViewModel.getPopularData()

        return binding.root
    }
}