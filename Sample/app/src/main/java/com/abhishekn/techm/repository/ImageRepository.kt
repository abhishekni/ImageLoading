package com.abhishekn.techm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhishekn.techm.db.ImageDao
import com.abhishekn.techm.model.ImageResponse
import com.abhishekn.techm.model.Row
import com.abhishekn.techm.network.ServiceApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository(private val imageDao: ImageDao, private val scope: CoroutineScope) {

    private val serviceApi by lazy {
        ServiceApi.create()
    }

    fun getImageData(): LiveData<List<Row>> {
        return imageDao.getAllImages()
    }

    // Call Image API, if data retrieved successfully then store it into DB
    fun loadImageApi(): MutableLiveData<Any> {
        val liveData = MutableLiveData<Any>()
        serviceApi.getImages().enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                liveData.postValue(t?.message)
            }

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                response?.let {
                    scope.launch(IO) {
                        if (it.code() == 200) {
                            val rows = response.body()?.rows
                            val row = rows?.filter {
                                it?.title != null
                            }
                            liveData.postValue(row)
                            insertData(row)
                        } else {
                            liveData.postValue("${it.code()}  Bad Request")
                        }
                    }
                }
            }
        })
        return liveData
    }

    // Insert Data into DB, before doing insert operation first remove all the data from DB
    private suspend fun insertData(rowImageData: List<Row?>?) {
        rowImageData?.let {
            imageDao.deleteAll()
            imageDao.insertImage(rowImageData)
        }
    }
}