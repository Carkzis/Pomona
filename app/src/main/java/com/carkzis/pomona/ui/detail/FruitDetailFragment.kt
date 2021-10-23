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
import com.carkzis.pomona.stats.UsageStatsManager
import com.carkzis.pomona.ui.DomainFruit
import com.carkzis.pomona.util.getFruitColourFilter
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
        // Stop the timer now that the View has been created, which will send the stats to the API.
        UsageStatsManager.generateDisplayEventStats()
        setUpFruitDetail(view)
    }

    /**
     * Collects the selected fruit details from SafeArgs, and sends
     * them to the FruitDetailViewModel.
     */
    private fun setUpFruitDetail(view: View) {
        val currentFruit =
            DomainFruit(args.fruitDetail[0], args.fruitDetail[1], args.fruitDetail[2])
        setUpBackgroundColour(view, currentFruit.type)
        viewModel.postFruitDetailsToLiveData(currentFruit)
    }

    private fun setUpBackgroundColour(view: View, type: String) {
        val detailsContainerView = view.findViewById<View>(R.id.clayout_fruit_detail)
        val fruitColourFilter = getFruitColourFilter(type, context!!)
        detailsContainerView.background.colorFilter = fruitColourFilter
    }


}