package com.example.whac_a_mole_game.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.whac_a_mole_game.R
import com.example.whac_a_mole_game.databinding.FragmentStartBinding

class StartFragment : BaseFragment<FragmentStartBinding>() {
    override val viewBinding: FragmentStartBinding
        get() = FragmentStartBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlay.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToGameFragment()
            viewModel.startGame()
            findNavController().navigate(action)
        }
        viewModel.amountOfHoles = sharedPref?.getInt(getString(R.string.amount_of_holes), 6)!!
        binding.switchAmountOfHoles.isChecked = viewModel.amountOfHoles != 6
        binding.switchAmountOfHoles.setOnCheckedChangeListener { _, isChecked ->
            viewModel.amountOfHoles = if (isChecked) 9 else 6
            with(sharedPref?.edit()) {
                this?.putInt(getString(R.string.amount_of_holes), viewModel.amountOfHoles!!)
                this?.apply()
            }
        }
        viewModel.record = sharedPref?.getInt(getString(R.string.record), 0)!!
        binding.tvRecord.text = if (viewModel.record==0) "There is no record" else "Record is ${viewModel.record}"
    }
}