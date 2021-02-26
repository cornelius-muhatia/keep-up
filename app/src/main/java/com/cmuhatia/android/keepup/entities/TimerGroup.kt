package com.cmuhatia.android.keepup.entities

import androidx.room.*

@Entity(tableName = "timer_group")
class TimerGroup {
        @PrimaryKey(autoGenerate = true)
        var  id: Long = 0L
        @ColumnInfo(name = "title")
        var title: String = "Timer Group"
        var active: Boolean = true
}

@Entity(tableName = "timer_entity",
        foreignKeys = [ForeignKey(entity = TimerGroup::class, parentColumns = arrayOf("id"), childColumns = arrayOf("group_id"))]
)
class TimerEntity {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L
        @ColumnInfo(name = "group_id")
        var groupId: Long = 0L
        var seconds: Long = 0L
        var label: String = "Testing"
        var active: Boolean = true

        /**
         * Convert seconds to readable format: 02:00:10
         */
        override fun toString(): String {
                val hr: Int = (seconds / (60 * 60)).toInt()
                var remSeconds: Long = seconds % (60 * 60)
                val min: Long = remSeconds / 60
                remSeconds %= 60
                return "$hr:$min:$remSeconds"
        }
}

data class GroupWithTimers(
        @Embedded val group: TimerGroup,
        @Relation(
                parentColumn = "id",
                entityColumn = "group_id"
        )
        val timers: List<TimerEntity>
)