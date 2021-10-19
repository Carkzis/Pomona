package com.carkzis.pomona

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.carkzis.pomona.databinding.FragmentFruitDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FruitDetailFragment : Fragment() {

    private val viewModel by viewModels<FruitDetailViewModel>()

    private lateinit var viewDataBinding: FragmentFruitDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentFruitDetailBinding.inflate(inflater, container, false)
            .apply {
                fruitDetailViewModel = viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return inflater.inflate(R.layout.fragment_fruit_detail, container, false)
    }


}