package com.example.week4assignment.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4assignment.R
import com.example.week4assignment.adapter.TrailersAdapter
import com.example.week4assignment.databinding.FragmentWatchMoviesBinding
import com.example.week4assignment.model.MovieViewModel
import com.example.week4assignment.utils.UIState
import com.squareup.picasso.Picasso


class WatchMoviesFragment : Fragment() {

    private val binding by lazy {
        FragmentWatchMoviesBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        TrailersAdapter {
            mMovieViewModel.site = it.site.toString()
            mMovieViewModel.key = it.key.toString()
        }
    }

    private lateinit var mMovieViewModel: MovieViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.webView.visibility = View.INVISIBLE
        binding.eventRecycler.alpha=0f
        mMovieViewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.IdView.text = mMovieViewModel.movieID.toString()
        binding.TitleView.text = mMovieViewModel.movieTitle
        binding.OverviewView.text = mMovieViewModel.movieOverView
        binding.ReleaseDateView.text = mMovieViewModel.movieReleaseDate
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w1280/" + mMovieViewModel.moviePoster)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding.ImageView)

        mMovieViewModel.status.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.LOADING -> {
                    binding.eventRecycler.visibility = View.GONE
                }
                is UIState.SUCCESS  -> {

                    binding.eventRecycler.visibility = View.VISIBLE
                    state.success
                }
                is UIState.SUCCESS2  -> {

                    binding.eventRecycler.visibility = View.VISIBLE
                    state.success?.let { adapter.update(it) }
                }
                is UIState.ERROR -> {

                    binding.eventRecycler.visibility = View.GONE
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error Loading Music")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry") { dialog, _ ->
                            mMovieViewModel.getTrailerData()
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }

            }
        }
        mMovieViewModel.getTrailerData()
        binding.webView.webChromeClient
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient
        binding.playButton.setOnClickListener {
            binding.webView.visibility = View.VISIBLE
            binding.webView.loadUrl("https://m.youtube.com/watch?v="+mMovieViewModel.key)
        }

        return binding.root
    }
}