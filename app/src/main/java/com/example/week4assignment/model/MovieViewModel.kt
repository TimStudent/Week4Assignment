package com.example.week4assignment.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.week4assignment.api.ApiHelper
import com.example.week4assignment.model.room.*
import com.example.week4assignment.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var readData: LiveData<List<MovieData>>
    private val _status: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val status: LiveData<UIState> get() = _status
    var movieDataList = ArrayList<MovieData>()
    private var movies = MovieData()
    private val repository: MovieDataRepository
    var movieID = 0
    var movieTitle = ""
    var movieOverView = ""
    var movieReleaseDate = ""
    var moviePoster = ""
    var site = ""
    var key = ""
    var movieduration = 0
    var popularity = 0.0
    var homepage = ""
    var genreList = emptyList<Genre>()

    init {
        val movieDao = MovieDataBase.getDatabase(application).movieDao()
        repository = MovieDataRepository(movieDao)
        readData = repository.readAllData
        initializeList()
    }

    private fun initializeList() {
        movieDataList.add(movies)
        readData = repository.readAllData
    }

    fun getMovieData(pageNumber: Int = 1){
        viewModelScope.launch(Dispatchers.IO){
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try{
                    val response = ApiHelper.serviceApi.getMovies(page = pageNumber)
                    if(response.isSuccessful){
                        response.body()?.let{ it ->
                            emit(UIState.SUCCESS(it.movieDomain.mapToMovieList(), null))
                        }
                    }
                    else{
                        //
                    }
                }catch (e: Exception){
                    emit(UIState.ERROR(e))
                }
            }
            flowHolder.collect {
                withContext(Dispatchers.Main){
                }
                _status.postValue(it)
            }
        }
    }

    fun getUpcomingData(pageNumber: Int = 1){
        viewModelScope.launch(Dispatchers.IO){
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try{
                    val response = ApiHelper.serviceApi.getUpcoming(page = pageNumber)
                    if (response.isSuccessful){
                        response.body()?.let{ it ->
                            emit(UIState.SUCCESS(it.movieDomain.mapToMovieList(), null))
                        }
                    }
                }
                catch (e: Exception){
                    emit(UIState.ERROR(e))
                }
            }
            flowHolder.collect{
                withContext(Dispatchers.Main){
                }
                _status.postValue(it)
            }
        }
    }

    fun getPopularData(pageNumber: Int = 1){
        viewModelScope.launch(Dispatchers.IO){
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try{
                    val response = ApiHelper.serviceApi.getPopular(page = pageNumber)
                    if (response.isSuccessful){
                        response.body()?.let{ it ->
                            emit(UIState.SUCCESS(it.movieDomain.mapToMovieList(), null))
                        }
                    }
                }
                catch (e: Exception){
                    emit(UIState.ERROR(e))
                }
            }
            flowHolder.collect{
                withContext(Dispatchers.Main){
                }
                _status.postValue(it)
            }
        }
    }

    fun getMovieDetailData(){
        viewModelScope.launch(Dispatchers.IO){
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try{
                    val response = ApiHelper.serviceApi.getMovieDetails(movieId = movieID)
                    if(response.isSuccessful){
                        response.body()?.let { it ->
                            emit(UIState.SUCCESS(null,null, DetailData(it.adult,
                                it.genres,it.popularity,it.homepage,it.id,it.runtime)))
                        }
                    }
                }catch (e: Exception){
                    emit(UIState.ERROR(e))
                }
            }
            flowHolder.collect{
                withContext(Dispatchers.Main){
                    _status.postValue(it)
                }
            }
        }
    }

    fun getTrailerData(){
        viewModelScope.launch(Dispatchers.IO){
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try{
                    val response = ApiHelper.serviceApi.getMovieTrailer(movieId = movieID)
                    if(response.isSuccessful){
                        response.body()?.let { it ->
                            emit(UIState.SUCCESS(null, it.trailerDomain.mapToTrailerList()))
                        }
                    }
                }catch (e: Exception){
                    emit(UIState.ERROR(e))
                }
            }
            flowHolder.collect{
                withContext(Dispatchers.Main){
                    _status.postValue(it)
                }
            }
        }
    }
}