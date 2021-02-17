package com.cmuhatia.android.keepup.ui.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmuhatia.android.keepup.entities.TimerEntity

class TimerViewModel : ViewModel() {

    val timerEntities = ArrayList<TimerEntity>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    /**
     * Playlist item click listener
     */
    fun onTimerClicked(item: TimerEntity){
        timerEntities.remove(item)
    }

    /**
     * Delete item from recycler view
     */
    fun onDeleteItem(item: TimerEntity){
        timerEntities.remove(item)
    }
}