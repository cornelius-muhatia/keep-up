package com.cmuhatia.android.keepup.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmuhatia.android.keepup.R
import com.cmuhatia.android.keepup.databinding.FragmentHomeBinding
import com.cmuhatia.android.keepup.repository.KeepUpDatabase
import com.cmuhatia.android.keepup.ui.timer.TimerListDialogFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val modelFactory = TimerGroupViewModelFactory(KeepUpDatabase.getInstance(application).timerGroupDao)
        homeViewModel =
            ViewModelProvider(this, modelFactory).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.newTimers.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_new_timer)
        }
        val adapter = GroupAdapter(homeViewModel, viewLifecycleOwner, TimerGroupListener { it ->
            val dialog = GroupDetailFragment(it)
            dialog.show(parentFragmentManager, "groupDetailGroup")
        })
        binding.groupList.layoutManager = LinearLayoutManager(activity)
        binding.groupList.adapter = adapter
        homeViewModel.timerGroups.observe(viewLifecycleOwner, Observer{
            it?.let {
            adapter.submitTimerGroups(it)
        }
        })
        return binding.root
    }
}