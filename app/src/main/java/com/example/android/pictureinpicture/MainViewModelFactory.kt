package com.example.android.pictureinpicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val stopwatchRepository: StopwatchRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(stopwatchRepository) as T
        }
        throw IllegalArgumentException("Incorrect view model class for Stopwatch")
    }
}
