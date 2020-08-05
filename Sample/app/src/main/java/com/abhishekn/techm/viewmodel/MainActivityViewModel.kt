package com.abhishekn.techm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.abhishekn.techm.db.ImageRoomDatabase
import com.abhishekn.techm.model.Row
import com.abhishekn.techm.repository.ImageRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String? = "MainActivityViewModel"
    private val repository: ImageRepository
    val imageData : LiveData<List<Row>>

    init {
        Log.i(TAG, "Init")
        val imageDao = ImageRoomDatabase.getDatabaseInstance(application, viewModelScope).imageDao()
        repository = ImageRepository(imageDao,viewModelScope)
        imageData = repository.getImageData()
    }

    fun loadImageAPI():LiveData<Any> =  repository.loadImageApi()
}