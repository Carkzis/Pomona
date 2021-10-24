package com.carkzis.pomona.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carkzis.pomona.databinding.FragmentFruitListBinding
import com.carkzis.pomona.stats.UsageStatsManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FruitListFragment : Fragment() {

    private val viewModel by viewModels<FruitListViewModel>()

    private lateinit var viewDataBinding: FragmentFruitListBinding

    private lateinit var fruitListAdapter: FruitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentFruitListBinding.inflate(inflater, container, false)
            .apply {
                fruitListViewModel = viewModel
                lifecycleOwner = viewLifecycleOwner
            }

        fruitListAdapter = setUpFruitListAdapter()
        viewDataBinding.fruitListRecyclerview.adapter = fruitListAdapter

        // Inflate the layout for this fragment.
        return viewDataBinding.root
    }

    private fun setUpFruitListAdapter(): FruitListAdapter {
        return FruitListAdapter(FruitListAdapter.OnClickListener {
            // Initiate the "timer" for generating stats on how long it takes to load next screen.
            UsageStatsManager.startDisplayEventStats()
            findNavController().navigate(
                // Pass through the current fruit's details.
                FruitListFragmentDirections.actionFruitListFragmentToFruitDetailFragment(
                    arrayOf(
                        it.type,
                        it.price,
                        it.weight
                    )
                )
            )
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UsageStatsManager.stopDisplayEventStats()
        setUpRefreshFab()
    }

    private fun setUpRefreshFab() {
        viewDataBinding.refreshFab.setOnClickListener {
            viewModel.refreshRepository()
        }
    }
}