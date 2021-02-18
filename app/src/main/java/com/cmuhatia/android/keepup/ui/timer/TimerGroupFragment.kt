package com.cmuhatia.android.keepup.ui.timer

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmuhatia.android.keepup.R
import com.cmuhatia.android.keepup.databinding.FragmentNewTimerBinding
import com.cmuhatia.android.keepup.entities.TimerEntity
import com.cmuhatia.android.keepup.repository.KeepUpDatabase
import timber.log.Timber

class TimerGroupFragment  : CallbackInterface, Fragment(){

    private lateinit var timerViewModel: TimerViewModel
    private lateinit var binding: FragmentNewTimerBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val timerModelFactory =
            TimerViewModelFactory(KeepUpDatabase.getInstance(application).timerGroupDao)

        timerViewModel = ViewModelProvider(this, timerModelFactory).get(TimerViewModel::class.java)
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
            if(timerViewModel.timerEntities.isEmpty()){
                binding.addTimerGroup.visibility = View.INVISIBLE
            }
        })
        binding.timersList.adapter = adapter
        binding.timersList.layoutManager = LinearLayoutManager(activity)
        binding.addTimerGroup.visibility = View.INVISIBLE

        binding.addTimerGroup.setOnClickListener {
            timerViewModel.submitGroup(binding.groupTitle.text.toString())
            findNavController().navigate(R.id.nav_home)
        }


        if(savedInstanceState != null && savedInstanceState.containsKey("timerEntities")){
            try {
                val list =
                    savedInstanceState.getSerializable("timerEntities") as ArrayList<TimerEntity>
                (binding.timersList.adapter as TimerAdapter).submitTimerList(ArrayList(list))
                if(binding.timerViewModel!!.timerEntities.isNotEmpty()){
                    binding.addTimerGroup.visibility = View.VISIBLE
                }
            } catch(ex: Exception) {
                Timber.e(ex, "Failed to load timer entities from bundle ")
            }
        }

        return binding.root
    }

    override fun callback(label: String, seconds: Long){
        binding.timerViewModel!!.timerEntities.add(
            TimerEntity(seconds, label))
        (binding.timersList.adapter as TimerAdapter).submitTimerList(ArrayList(binding.timerViewModel!!.timerEntities))
        if(binding.timerViewModel!!.timerEntities.isNotEmpty()){
            binding.addTimerGroup.visibility = View.VISIBLE
        }
    }

    override  fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(this::binding.isInitialized) {
            outState.putSerializable("timerEntities", binding.timerViewModel!!.timerEntities)
        }
    }
}


