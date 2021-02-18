package com.cmuhatia.android.keepup.repository

import androidx.room.Dao
import androidx.room.Insert
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.entities.TimerGroup

@Dao
interface TimerGroupDao {

    /**
     * Insert a list of timers
     * @param timers a List of timers
     */
    @Insert
    fun insertAll(timers: List<TimerEntity>)

    /**
     * Insert a timer group
     * @param group {@link TimerGroup}
     */
    @Insert
    fun insert(group: TimerGroup)
}