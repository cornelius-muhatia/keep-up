package com.cmuhatia.android.keepup.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.entities.TimerGroup
import kotlinx.coroutines.flow.Flow
import java.sql.Time
import java.util.*

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
    fun insert(group: TimerGroup): Long

    /**
     * Fetch all timer groups
     */
    @Query("select * from timer_group")
    fun findAll(): LiveData<List<TimerGroup>>

    /**
     * Count group timers
     *
     * @param groupId group id
     */
    @Query("select count(id) from timer_entity where group_id = :groupId")
    fun countGroupTimers(groupId: Long): LiveData<Int>

    /**
     * Delete timers by group id
     *
     * @param groupId id
     */
    @Query("delete from timer_entity where group_id = :groupId")
    fun deleteTimerEntity(groupId: Long)

    /**
     * Delete group by id
     * @param groupId Group primary key
     */
    @Query("delete from timer_group where id = :groupId")
    fun deleteGroup(groupId: Long)

    /**
     * Delete group and timers associated with it
     *
     * @param groupId group id
     */
    @Transaction
    fun deleteGroupAndTimers(groupId: Long){

        deleteTimerEntity(groupId)
        deleteGroup(groupId)
    }

    @Query("select * from timer_entity where group_id=:groupId")
    fun groupTimers(groupId: Long): LiveData<List<TimerEntity>>

    /**
     * Update group timers status
     */
    @Query("update timer_entity set active = :status where group_id = :groupId")
    fun updateGroupTimersStatus(status: Boolean, groupId: Long)

    /**
     * Fetch active timers
     */
    @Query("select * from timer_entity where active = 'true'")
    fun getActiveTimers(): Flow<List<TimerEntity>>

    /**
     * Fetch {@link TimerEntity} using id
     */
    @Query("select * from timer_entity where id=:id")
    fun getTimer(id: Long): TimerEntity

    /**
     * Load live timer entity
     */
    @Query("select * from timer_entity where id = :id")
    fun getLiveTimer(id: Long): LiveData<TimerEntity>

    /**
     * Fetch timers by group status
     *
     * @param isActive group status
     */
    @Query("select t.* from timer_entity t inner join timer_group g on g.id = t.group_id where g.active = :isActive")
    fun getTimers(isActive: Boolean): List<TimerEntity>

    /**
     * Update timers
     *
     * @param timers a list of timers
     */
    @Update
    fun updateTimers(vararg timers: TimerEntity)

    /**
     * Get group timer
     * @param id group timer id
     */
    @Query("select * from timer_group where id = :id")
    fun getGroupTimer(id: Long): TimerGroup

    @Query("update timer_group set active = :active where id = :id")
    fun updateGroupStatus(id: Long, active: Boolean)

    /**
     * Update group and timers status
     *
     * @param groupId group id
     * @param active true for active otherwise false
     */
    @Transaction
    fun updateGroupAndTimerStatus(groupId: Long, active: Boolean){
        updateGroupStatus(groupId, active)
        updateGroupTimersStatus(active, groupId)
    }


}