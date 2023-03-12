package com.example.whac_a_mole_game.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.whac_a_mole_game.R
import com.example.whac_a_mole_game.databinding.FragmentGameBinding
import com.example.whac_a_mole_game.models.Mole
import com.example.whac_a_mole_game.util.Constants.Companion.FRAGMENT_PAUSE
import kotlinx.coroutines.launch

class GameFragment : BaseFragment<FragmentGameBinding>(), MoleAdapter.OnMoleClickListener {
    override val viewBinding: FragmentGameBinding
        get() = FragmentGameBinding.inflate(layoutInflater)
    private lateinit var moleAdapter: MoleAdapter

    override fun onMoleClick(mole: Mole) {
        if (mole.isAppeared) {
            viewModel.increaseScore()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.pauseGame()
                showPauseDialogFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var amountOfHoles = sharedPref?.getInt(getString(R.string.amount_of_holes), 6)
        viewModel.amountOfHoles = amountOfHoles!!
        moleAdapter = MoleAdapter(this)
        binding.rvMoles.apply {
            adapter = moleAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
        viewModel.moles.observe(viewLifecycleOwner) { moles ->
            moleAdapter.submitList(moles)
        }
        viewModel.score.observe(viewLifecycleOwner) { score ->
            binding.tvScore.text = "Score: $score"
        }
        viewModel.secondsOfTimer.observe(viewLifecycleOwner) { seconds ->
            if (seconds <= 5) {
                binding.tvTimer.setTextColor(Color.RED)
            }
            binding.tvTimer.text = "Time: $seconds"
        }
        viewModel.isGameEnded.observe(viewLifecycleOwner) { isGameEnded ->
            if (isGameEnded) {
                if (viewModel.score.value!! > viewModel.record) {
                    viewModel.record = viewModel.score.value!!
                    with(sharedPref?.edit()) {
                        this?.putInt(getString(R.string.record), viewModel.score.value!!)
                        this?.apply()
                    }
                }
                val action = GameFragmentDirections.actionGameFragmentToEndFragment()
                findNavController().navigate(action)
            }
        }
        viewModel.isGameResumed.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.setMolesToAppear()
            }
        }
        binding.tvRecord.text = "Record: ${viewModel.record}"
    }

    override fun onPause() {
        super.onPause()
        if (childFragmentManager.findFragmentByTag(FRAGMENT_PAUSE) == null) {
            showPauseDialogFragment()
            viewModel.pauseGame()
        }
    }

    private fun showPauseDialogFragment() {
        PauseDialogFragment().show(childFragmentManager, FRAGMENT_PAUSE)
    }
}