package com.example.whac_a_mole_game.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.whac_a_mole_game.databinding.DialogFragmentPauseBinding
import com.example.whac_a_mole_game.ui.GameViewModel

class PauseDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentPauseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogFragmentPauseBinding.inflate(layoutInflater)
        // Set transparent background
        if (dialog != null && dialog?.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnResume.setOnClickListener {
            viewModel.resumeGame()
            this.dismiss()
        }
        binding.btnGoToMenu.setOnClickListener {
            val action = GameFragmentDirections.actionGameFragmentToStartFragment()
            findNavController().navigate(action)
            viewModel.startGame()
        }
        binding.btnPlayAgain.setOnClickListener {
            viewModel.startGame()
            this.dismiss()
        }
        binding.btnExit.setOnClickListener {
            activity?.finish()
        }
    }
}