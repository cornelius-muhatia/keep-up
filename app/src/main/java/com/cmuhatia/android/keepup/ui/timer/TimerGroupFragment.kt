package com.cmuhatia.android.keepup.ui.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmuhatia.android.keepup.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber

class TimerGroupFragment  : CallbackInterface, Fragment(){

    private lateinit var galleryViewModel: GalleryViewModel
    private val timerLabels = ArrayList<TimerLabel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_timer, container, false)
        val addTimer: FloatingActionButton = root.findViewById(R.id.add_timer)
        addTimer.setOnClickListener {
            val dialog = TimerListDialogFragment(this)
            dialog.show(parentFragmentManager, "timerDialog")
        }

        val recyclerView: RecyclerView = root.findViewById(R.id.timersList)
        timerLabels.add(TimerLabel("12:00:00", 444))
        timerLabels.add(TimerLabel("12:00:40", 33535))
        val adapter = TimerAdapter(timerLabels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        Timber.i("Initialized timer recycler")

        return root
    }

    override fun callback(label: String, seconds: Long){
        timerLabels.add(TimerLabel(label, seconds))
    }
}

class TimerAdapter(private val timers: List<TimerLabel>) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerAdapter.ViewHolder {
        val timerItemContent = LayoutInflater.from(parent.context).inflate(R.layout.content_timer_item, parent, false)
        return ViewHolder(timerItemContent)
    }

    override fun onBindViewHolder(holder: TimerAdapter.ViewHolder, position: Int) {
        Log.i(javaClass.simpleName,"Adding new item at position $position")
        val timerItem: TimerLabel = timers[position]
        holder.timerItemLabel.text = timerItem.label
    }

    override fun getItemCount(): Int {
        return timers.size
    }

    inner class ViewHolder(listView: View) :  RecyclerView.ViewHolder(listView){
            val timerItemLabel: TextView = listView.findViewById(R.id.timerItemLabel)
    }

}

class TimerLabel(var label: String, public var seconds: Long){

}