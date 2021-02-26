package com.cmuhatia.android.keepup.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmuhatia.android.keepup.databinding.TimerGroupItemBinding
import com.cmuhatia.android.keepup.entities.TimerGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupAdapter(val homeModel: HomeViewModel, val lifecycle: LifecycleOwner, val clickListener: TimerGroupListener)  : ListAdapter<TimerGroup,
        RecyclerView.ViewHolder>(GroupItemCallbackDiff())  {
    /**
     * Default coroutine dispatcher
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimerGroupViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as TimerGroup
        when(holder){
            is TimerGroupViewHolder -> {
                holder.bind(item, homeModel, lifecycle, clickListener)
            }
        }
    }

    fun submitTimerGroups(list: List<TimerGroup>){
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }
}

class GroupItemCallbackDiff : DiffUtil.ItemCallback<TimerGroup>() {
    override fun areItemsTheSame(oldItem: TimerGroup, newItem: TimerGroup): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: TimerGroup, newItem: TimerGroup): Boolean {
        return (oldItem.id == newItem.id)
    }
}

class TimerGroupViewHolder(private val viewDataBinding: TimerGroupItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(item: TimerGroup, homeModel: HomeViewModel, lifecycle: LifecycleOwner, clickListener: TimerGroupListener){
        viewDataBinding.clickListener = clickListener
        viewDataBinding.timerGroup = item
        viewDataBinding.homeViewModel = homeModel
        homeModel.getTimerCount(item.id).observe(lifecycle, Observer{ it?.let {
            viewDataBinding.groupTimerCounts= it
        }})
        viewDataBinding.groupTimerSwitch.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                homeModel.startGroupTimers(item.id)
            } else {
                homeModel.stopGroupTimers(item.id)
            }
        }
        viewDataBinding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): TimerGroupViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TimerGroupItemBinding.inflate(layoutInflater, parent, false)
            return TimerGroupViewHolder(binding)
        }
    }

}

class TimerGroupListener(val clickListener: (entity: TimerGroup) -> Unit){
    fun onClick(item: TimerGroup) = clickListener(item)
}