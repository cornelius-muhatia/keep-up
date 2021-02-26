package com.cmuhatia.android.keepup.ui.home

import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmuhatia.android.keepup.databinding.FragmentGroupDetailsItemBinding
import com.cmuhatia.android.keepup.entities.TimerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupDetailAdapter(val homeModel: HomeViewModel, val lifecycle: LifecycleOwner) : ListAdapter<TimerEntity,
        RecyclerView.ViewHolder>(GroupDetailItemCallbackDiff())   {

    /**
     *  Default coroutine dispatcher
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as TimerEntity
        when(holder){
            is GroupDetailViewHolder -> {
                holder.bind(item, homeModel, lifecycle)
            }
        }
    }

    /**
     * Add a new list of {@link TimerEntity}
     */
    fun submitTimers(list: List<TimerEntity>){
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }
}

class GroupDetailItemCallbackDiff : DiffUtil.ItemCallback<TimerEntity>() {
    override fun areItemsTheSame(oldItem: TimerEntity, newItem: TimerEntity): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: TimerEntity, newItem: TimerEntity): Boolean {
        return (oldItem.seconds == newItem.seconds)
    }
}

class GroupDetailViewHolder(private val viewDataBinding: FragmentGroupDetailsItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(item: TimerEntity, homeViewModel: HomeViewModel, lifecycle: LifecycleOwner){
        viewDataBinding.timerLabel.text = item.toString()
        viewDataBinding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): GroupDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentGroupDetailsItemBinding.inflate(layoutInflater, parent, false)
            return GroupDetailViewHolder(binding)
        }
    }

}