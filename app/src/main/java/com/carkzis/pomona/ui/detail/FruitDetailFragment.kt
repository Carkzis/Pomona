package com.carkzis.pomona.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.carkzis.pomona.R
import com.carkzis.pomona.databinding.FragmentFruitDetailBinding
import com.carkzis.pomona.ui.DomainFruit
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FruitDetailFragment : Fragment() {

    private val viewModel by viewModels<FruitDetailViewModel>()

    private lateinit var viewDataBinding: FragmentFruitDetailBinding

    private val args: FruitDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentFruitDetailBinding.inflate(inflater, container, false)
            .apply {
                fruitDetailViewModel = viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFruitDetail()
    }

    /**
     * Collects the selected fruit details from SafeArgs, and sends
     * them to the FruitDetailViewModel.
     */
    private fun setUpFruitDetail() {
        val currentFruit =
            DomainFruit(args.fruitDetail[0], args.fruitDetail[1], args.fruitDetail[2])
        Timber.e(currentFruit.price)
        viewModel.postFruitDetailsToLiveData(currentFruit)
    }



}