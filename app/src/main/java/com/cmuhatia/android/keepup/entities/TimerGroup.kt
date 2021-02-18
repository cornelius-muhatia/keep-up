package com.cmuhatia.android.keepup.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TimerGroup constructor(
        val title: String
) {
        @PrimaryKey(autoGenerate = true)
        var  id: Long = 0L
}

@Entity
class TimerEntity constructor(
        val seconds: Long,
        val label: String = ""
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L
        @ColumnInfo(name = "group_id")
        var groupId: Long = 0L
}