package com.cmuhatia.android.keepup.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmuhatia.android.keepup.R
import com.cmuhatia.android.keepup.databinding.FragmentGroupDetailsBinding
import com.cmuhatia.android.keepup.entities.TimerGroup
import com.cmuhatia.android.keepup.repository.KeepUpDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class GroupDetailFragment(private val timerGroup: TimerGroup) : BottomSheetDialogFragment() {

    /**
     * Group detail fragment binder
     */
    private lateinit var binding: FragmentGroupDetailsBinding

    /**
     *  {@link HomeViewModel} for loading Group timers
     */
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_details, container, false)

        val application = requireNotNull(this.activity).application
        val modelFactory = TimerGroupViewModelFactory(KeepUpDatabase.getInstance(application).timerGroupDao)
        homeViewModel =
            ViewModelProvider(this, modelFactory).get(HomeViewModel::class.java)

        val adapter = GroupDetailAdapter(homeViewModel, viewLifecycleOwner)
        binding.groupTimersList.layoutManager = LinearLayoutManager(activity)
        binding.groupTimersList.adapter = adapter
        homeViewModel.getGroupTimers(timerGroup.id).observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitTimers(it)
            }
        })
        return binding.root
    }
}