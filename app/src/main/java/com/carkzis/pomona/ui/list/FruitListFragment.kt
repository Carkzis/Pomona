package com.carkzis.pomona.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.carkzis.pomona.databinding.FragmentFruitListBinding
import com.carkzis.pomona.stats.UsageStatsManager
import com.carkzis.pomona.util.showToast
import dagger.hilt.android.AndroidEntryPoint

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

    /**
     * This sets up the FruitListAdapter by passing it an OnClickListener that will
     * navigate the user to the FruitDetailFragment when the view is clicked.
     */
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
        setUpToast()
    }

    /**
     * Sets an OnClickListener to the Floating Action Bar, which will attempt to refresh
     * the repository.
     */
    private fun setUpRefreshFab() {
        viewDataBinding.refreshFab.setOnClickListener {
            viewModel.refreshRepository()
        }
    }

    /**
     * Observes the toastText LiveData for an Event, and shows a toast to the user using
     * the provided string ID.
     */
    private fun setUpToast() {
        viewModel.toastText.observe(viewLifecycleOwner, {
            it.getContextIfNotHandled()?.let { message ->
                context?.showToast(requireContext().getString(message))
            }
        })
    }

}