package com.example.android.pictureinpicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val stopwatchRepository: StopwatchRepository, private val clockRepository: ClockRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(stopwatchRepository, clockRepository) as T
        }
        throw IllegalArgumentException("Incorrect view model class for Stopwatch")
    }
}
