package com.cmuhatia.android.keepup.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmuhatia.android.keepup.databinding.ContentTimerItemBinding
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.ui.timer.TimerItemCallbackDiff
import com.cmuhatia.android.keepup.ui.timer.TimerItemListener

class GroupAdapter()  : ListAdapter<TimerEntity,
        RecyclerView.ViewHolder>(TimerItemCallbackDiff())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class TimerItemViewHolder(private val viewDataBinding: ContentTimerItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(item: TimerEntity, clickListener: TimerItemListener){
        viewDataBinding.item = item
        viewDataBinding.clickListener = clickListener
        viewDataBinding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): TimerItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ContentTimerItemBinding.inflate(layoutInflater, parent, false)
            return TimerItemViewHolder(binding)
        }
    }

}