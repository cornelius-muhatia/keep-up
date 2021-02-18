package com.cmuhatia.android.keepup.ui.timer

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.entities.TimerGroup
import com.cmuhatia.android.keepup.repository.KeepUpDatabase
import com.cmuhatia.android.keepup.repository.TimerGroupDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TimerViewModel(private val timerRepo: TimerGroupDao) : ViewModel() {

    val timerEntities = ArrayList<TimerEntity>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()
    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     */
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    /**
     * Delete item from recycler view
     */
    fun onDeleteItem(item: TimerEntity){
        timerEntities.remove(item)
    }

    /**
     * Create group and timers
     */
    fun submitGroup(groupTitle: String){
        ioScope.launch {
            val title = if (groupTitle.isBlank()) "Group" else groupTitle
            val group = TimerGroup(title)
            timerRepo.insert(group)
            timerEntities.forEach { entity ->
                entity.groupId = group.id
            }
            timerRepo.insertAll(timerEntities)

        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}