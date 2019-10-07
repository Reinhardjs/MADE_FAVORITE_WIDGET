package com.example.made_final.viewmodel

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.made_final.data.ApiConstants
import io.reactivex.Single
import org.json.JSONObject

class MovieDetailViewModel : MovieListViewModel() {

    fun getReviews(movieId: Int, queue: RequestQueue): Single<JSONObject> {
        return Single.create {

            val url = "https://api.themoviedb.org/3/movie/" +
                    movieId +
                    "/reviews?" +
                    "api_key=" + ApiConstants.API_KEY + "&language=en-US&page=1"

            val getRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        // display response
                        // Log.d("Response", response.toString());

                        it.onSuccess(response)

                    },
                    Response.ErrorListener {
                        it.printStackTrace()
                    }
            )

            queue.add(getRequest)

        }
    }

    fun getTrailers(movieId: Int, queue: RequestQueue): Single<JSONObject> {
        return Single.create {

            val url = "https://api.themoviedb.org/3/movie/" +
                    movieId +
                    "/videos?" +
                    "api_key=" + ApiConstants.API_KEY + "&language=en-US&page=1"

            val getRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->

                        it.onSuccess(response)

                    },
                    Response.ErrorListener {
                        it.printStackTrace()
                    }
            )

            queue.add(getRequest)

        }
    }

}