package com.carkzis.pomona

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.carkzis.pomona.databinding.FragmentFruitListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FruitListFragment : Fragment() {

    private val viewModel by viewModels<FruitListViewModel>()

    private lateinit var viewDataBinding: FragmentFruitListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentFruitListBinding.inflate(inflater, container, false)
            .apply {
                fruitListViewModel = viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        // Inflate the layout for this fragment.
        return viewDataBinding.root
    }

}