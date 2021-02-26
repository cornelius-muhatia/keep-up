package com.cmuhatia.android.keepup.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cmuhatia.android.keepup.repository.TimerGroupDao
import com.cmuhatia.android.keepup.ui.timer.TimerViewModel

class TimerGroupViewModelFactory (private val timerRepo: TimerGroupDao) : ViewModelProvider.Factory  {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(timerRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}