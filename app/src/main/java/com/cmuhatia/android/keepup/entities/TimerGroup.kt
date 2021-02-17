package com.cmuhatia.android.keepup.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TimerGroup constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val title: String
) {
}

class TimerEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val seconds: Long,
        val label: String = ""
)