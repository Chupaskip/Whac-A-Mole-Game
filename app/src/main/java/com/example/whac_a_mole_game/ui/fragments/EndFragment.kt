package com.example.whac_a_mole_game.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.whac_a_mole_game.databinding.FragmentEndBinding
import com.example.whac_a_mole_game.databinding.FragmentGameBinding

class EndFragment : BaseFragment<FragmentEndBinding>() {
    override val viewBinding: FragmentEndBinding
        get() = FragmentEndBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlayAgain.setOnClickListener {
            val action = EndFragmentDirections.actionEndFragmentToGameFragment()
            viewModel.startGame()
            findNavController().navigate(action)
        }
        binding.btnGoToMenu.setOnClickListener {
            val action = EndFragmentDirections.actionEndFragmentToStartFragment()
            findNavController().navigate(action)
        }
        binding.tvScore.text = "Score is ${viewModel.score.value}"
        binding.tvRecord.text = "Record is ${viewModel.record}"
    }
}