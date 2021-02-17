package com.cmuhatia.android.keepup

import android.text.format.DateFormat
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmuhatia.android.keepup.entities.TimerEntity


@BindingAdapter("secondsToText")
fun TextView.secondsToText(item: TimerEntity?) {
    item?.let {
        text = item.label
    }
}
