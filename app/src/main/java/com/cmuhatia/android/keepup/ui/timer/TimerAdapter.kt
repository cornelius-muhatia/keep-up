package com.cmuhatia.android.keepup.ui.timer

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmuhatia.android.keepup.databinding.ContentTimerItemBinding
import com.cmuhatia.android.keepup.entities.TimerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerAdapter(val clickListener: TimerItemListener) : ListAdapter<TimerEntity,
        RecyclerView.ViewHolder>(TimerItemCallbackDiff()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimerItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as TimerEntity
        when(holder){
            is TimerItemViewHolder -> {
                holder.bind(item, clickListener)
            }
        }
    }

    fun submitTimerList(items: List<TimerEntity>){
        adapterScope.launch {
            Log.i(this.javaClass.simpleName, "Adding new item " + items.size)
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


}

class TimerItemCallbackDiff : DiffUtil.ItemCallback<TimerEntity>() {
    override fun areItemsTheSame(oldItem: TimerEntity, newItem: TimerEntity): Boolean {
        Log.i(this.javaClass.simpleName, "Item comparison " + oldItem.seconds + " vs " + newItem.seconds)
        return (oldItem.seconds == newItem.seconds)
    }

    override fun areContentsTheSame(oldItem: TimerEntity, newItem: TimerEntity): Boolean {
//        return oldItem.equals(newItem)
        return (oldItem.seconds == newItem.seconds)
    }
}

class TimerItemViewHolder(private val viewDataBinding: ContentTimerItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(item: TimerEntity, clickListener: TimerItemListener){
        viewDataBinding.item = item
        viewDataBinding.executePendingBindings()
        viewDataBinding.clickListener = clickListener
    }
    companion object {
        fun from(parent: ViewGroup): TimerItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ContentTimerItemBinding.inflate(layoutInflater, parent, false)
            return TimerItemViewHolder(binding)
        }
    }

}

class TimerItemListener(val clickListener: (entity: TimerEntity) -> Unit){
    fun onClick(item: TimerEntity) = clickListener(item)
}