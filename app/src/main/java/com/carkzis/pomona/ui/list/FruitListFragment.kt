package com.carkzis.pomona.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.carkzis.pomona.databinding.FragmentFruitListBinding
import com.carkzis.pomona.ui.FruitListAdapter
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

    private fun setUpFruitListAdapter() : FruitListAdapter {
        return FruitListAdapter(FruitListAdapter.OnClickListener{
            // TODO: Navigation to FruitDetailFragment. SafeArgs?
        })
    }

}