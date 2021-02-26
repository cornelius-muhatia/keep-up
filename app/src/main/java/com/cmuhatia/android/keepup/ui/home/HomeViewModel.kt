package com.cmuhatia.android.keepup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.entities.TimerGroup
import com.cmuhatia.android.keepup.repository.TimerGroupDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val timerRepo: TimerGroupDao) : ViewModel() {
    /**
     * Timer groups live data
     */
    var timerGroups: LiveData<List<TimerGroup>> = timerRepo.findAll()

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
     * Count group timers
     *
     * @param groupId timer group id
     */
    fun getTimerCount(groupId: Long): LiveData<Int>{
        return timerRepo.countGroupTimers(groupId)
    }

    /**
     * Delete group and timers associate with it.
     *
     * @param groupId group id
     */
    fun deleteGroup(groupId: Long){
        ioScope.launch {
            timerRepo.deleteGroupAndTimers(groupId)
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

    /**
     * Return live data of group timers
     *
     * @param groupId Group Id
     */
    fun getGroupTimers(groupId: Long): LiveData<List<TimerEntity>>{
        return timerRepo.groupTimers(groupId)
    }

    /**
     * Start group timers
     *
     * @param groupId group timer id
     */
    fun startGroupTimers(groupId: Long){
        ioScope.launch {
            timerRepo.updateGroupTimersStatus(true, groupId)
        }
    }

    /**
     * Stop group timers
     *
     * @param groupId group timer id
     */
    fun stopGroupTimers(groupId: Long){
        ioScope.launch {
            timerRepo.updateGroupAndTimerStatus(groupId, false)
        }
    }

    /**
     * Get live timer entity
     *
     * @param id timer id
     */
    fun getLiveTimer(id: Long): LiveData<TimerEntity>{
        return timerRepo.getLiveTimer(id)
    }
}