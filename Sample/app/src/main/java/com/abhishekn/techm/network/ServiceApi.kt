package com.abhishekn.techm.network

import com.abhishekn.techm.model.ImageResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json

const val BASE_URL = "https://dl.dropboxusercontent.com/"

interface ServiceApi {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getImages() : Call<ImageResponse>

    companion object{
        fun create(): ServiceApi{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ServiceApi::class.java)
        }

    }

}