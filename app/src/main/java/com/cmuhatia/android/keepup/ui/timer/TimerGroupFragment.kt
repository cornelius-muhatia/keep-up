package com.cmuhatia.android.keepup.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmuhatia.android.keepup.R
import com.cmuhatia.android.keepup.databinding.FragmentNewTimerBinding
import com.cmuhatia.android.keepup.entities.TimerEntity

class TimerGroupFragment  : CallbackInterface, Fragment(){

    private lateinit var timerViewModel: TimerViewModel
    private lateinit var binding: FragmentNewTimerBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        timerViewModel =
                ViewModelProvider(this).get(TimerViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_timer, container, false)
        binding.lifecycleOwner = this
        binding.timerViewModel = timerViewModel
        binding.addTimer.setOnClickListener {
            val dialog = TimerListDialogFragment(this)
            dialog.show(parentFragmentManager, "timerDialog")
        }

        val adapter = TimerAdapter(TimerItemListener{ entity: TimerEntity ->
            timerViewModel.onDeleteItem(entity)
            (binding.timersList.adapter as TimerAdapter).submitTimerList(timerViewModel.timerEntities)
        })
        binding.timersList.adapter = adapter
        binding.timersList.layoutManager = LinearLayoutManager(activity)
        binding.addTimerGroup.visibility = View.INVISIBLE

        return binding.root
    }

    override fun callback(label: String, seconds: Long){
        binding.timerViewModel!!.timerEntities.add(
            TimerEntity((binding.timerViewModel!!.timerEntities.size.toLong()), seconds, label))
        (binding.timersList.adapter as TimerAdapter).submitTimerList(ArrayList(binding.timerViewModel!!.timerEntities))
        if(binding.timerViewModel!!.timerEntities.isNotEmpty()){
            binding.addTimerGroup.visibility = View.VISIBLE
        } else {
            binding.addTimerGroup.visibility = View.INVISIBLE
        }
    }
}


