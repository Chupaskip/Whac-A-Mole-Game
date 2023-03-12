package com.example.whac_a_mole_game.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.whac_a_mole_game.ui.GameViewModel

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    abstract val viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = viewBinding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected val sharedPref by lazy {
        activity?.getPreferences(Context.MODE_PRIVATE)
    }

    protected val viewModel: GameViewModel by activityViewModels()
}
