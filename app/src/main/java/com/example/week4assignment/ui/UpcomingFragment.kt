package com.example.week4assignment.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week4assignment.adapter.MovieAdapter
import com.example.week4assignment.databinding.FragmentUpcomingBinding
import com.example.week4assignment.model.MovieViewModel
import com.example.week4assignment.utils.UIState

class UpcomingFragment : Fragment() {

    private val binding by lazy {
        FragmentUpcomingBinding.inflate(layoutInflater)
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
    private lateinit var recyclerView: RecyclerView
    private var page = 1
    private lateinit var listState: Parcelable
    private var itemCount: Int = 0
    private lateinit var mMovieViewModel: MovieViewModel
    private var pageCount = 1
    private var newCount = 0

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
                    state.success?.let { adapter.add(it) }
                }
                is UIState.ERROR -> {
                    binding.loading.visibility = View.GONE
                    binding.eventRecycler.visibility = View.GONE
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error Loading")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry") { dialog, _ ->
                            mMovieViewModel.getUpcomingData(page)
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d(ContentValues.TAG, "Page Number is : " + page.toString())
        if (itemCount == 0 && recyclerView.layoutManager?.itemCount == 0) {
            mMovieViewModel.getUpcomingData(page)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    page++
                    mMovieViewModel.getUpcomingData(page)
                    Log.d(
                        ContentValues.TAG,
                        "test 1:" + recyclerView.layoutManager?.itemCount.toString()
                    )
                    Log.d(ContentValues.TAG, "test 2:" + page.toString())
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = recyclerView.layoutManager?.onSaveInstanceState()!!
        outState.putInt("itemCountKey", recyclerView.layoutManager?.itemCount!!)
        outState.putParcelable("KEY", listState)
        outState.putInt("pageKEY", page)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        try {
            listState = savedInstanceState?.getParcelable("KEY")!!
            recyclerView.layoutManager?.onRestoreInstanceState(listState)
            newCount = savedInstanceState.getInt("itemCountKey")
            pageCount = savedInstanceState.getInt("pageKEY")
            itemCount = newCount
            Log.d(ContentValues.TAG, "test 3:$itemCount")
            page = pageCount
            Log.d(ContentValues.TAG, "test 4:$page")
        } catch (e: Exception) {

        }

    }
}