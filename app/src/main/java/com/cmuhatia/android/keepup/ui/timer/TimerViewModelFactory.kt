package com.cmuhatia.android.keepup.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cmuhatia.android.keepup.repository.TimerGroupDao

class TimerViewModelFactory(private val timerRepo: TimerGroupDao) : ViewModelProvider.Factory  {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(timerRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}