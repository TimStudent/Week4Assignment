package com.example.week4assignment.api

import com.example.week4assignment.model.DetailsData
import com.example.week4assignment.model.MoviesData
import com.example.week4assignment.model.TrailersData
import com.example.week4assignment.model.room.MovieData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHelper {

    @GET(PATH_SEARCH)
    suspend fun getMovies(
        @Query("api_key") api_key: String = "819950d4cf35be1fb70d8746bc0796bf",
        @Query("language") language: String = "en-us",
        @Query("page") page: Int = 1
    ): Response<MoviesData>

    @GET(PATH_SEARCH2)
    suspend fun getUpcoming(
        @Query("api_key") api_key: String = "819950d4cf35be1fb70d8746bc0796bf",
        @Query("language") language: String = "en-us",
        @Query("page") page: Int = 1
    ) : Response<MoviesData>

    @GET(PATH_SEARCH3)
    suspend fun getPopular(
        @Query("api_key") api_key: String = "819950d4cf35be1fb70d8746bc0796bf",
        @Query("language") language: String = "en-us",
        @Query("page") page: Int = 1
    ) : Response<MoviesData>

    @GET("{movieID}/videos?")
    suspend fun getMovieTrailer(
        @Path("movieID") movieId: Int = 0,
        @Query("api_key") api_key: String = "819950d4cf35be1fb70d8746bc0796bf",
        @Query("language") language: String = "en-us"
    ): Response<TrailersData>

    @GET("{movieID}?")
    suspend fun getMovieDetails(
        @Path("movieID") movieId: Int = 0,
        @Query("api_key") api_key: String = "819950d4cf35be1fb70d8746bc0796bf",
        @Query("language") language: String = "en-us"
    ): Response<DetailsData>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val PATH_SEARCH = "now_playing?"
        private const val PATH_SEARCH2 = "upcoming?"
        private const val PATH_SEARCH3 = "popular?"
        val serviceApi: ApiHelper = ApiService.movieService
    }
}