package com.example.whac_a_mole_game.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whac_a_mole_game.models.Mole
import com.example.whac_a_mole_game.util.Constants.Companion.INTERVAL_OF_MOLES
import com.example.whac_a_mole_game.util.Constants.Companion.SCORE_INC
import kotlinx.coroutines.delay


class GameViewModel : ViewModel() {
    val moles = MutableLiveData<List<Mole>>()
    private val _moles = mutableListOf<Mole>()

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _secondsOfTimer = MutableLiveData(30)
    val secondsOfTimer: LiveData<Int> get() = _secondsOfTimer

    var amountOfHoles = 0
    var record = 0

    private lateinit var timer: CountDownTimer

    private val _isGameResumed = MutableLiveData<Boolean>()
    val isGameResumed: LiveData<Boolean> get() = _isGameResumed

    private val _isGameEnded = MutableLiveData(false)
    val isGameEnded: LiveData<Boolean> get() = _isGameEnded

    fun startGame() {
        _secondsOfTimer.postValue(30)
        _score.postValue(0)
        _isGameEnded.value = false
        resumeGame(true)
    }

    fun resumeGame(isTimerReset: Boolean = false) {
        startTimer(isTimerReset)
        _isGameResumed.value = true
    }

    fun pauseGame() {
        timer.cancel()
        _isGameResumed.value = false
    }

    private fun startTimer(isTimerReset: Boolean = false) {
        val duration = if (!isTimerReset) _secondsOfTimer.value!! else 30
        timer = object : CountDownTimer(duration * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsOfTimer.postValue((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                _isGameEnded.postValue(true)
            }
        }
        timer.start()
    }

    suspend fun setMolesToAppear() {
        while (_isGameResumed.value == true) {
            setMolesWithRandomAppeared()
            delay(INTERVAL_OF_MOLES)
        }
    }

    private fun setMolesWithRandomAppeared() {
        if (amountOfHoles != 0) {
            _moles.clear()
            for (i in 0 until amountOfHoles) {
                _moles.add(Mole(i))
            }
            val positionOfMoleToAppear = (0 until amountOfHoles).random()
            _moles[positionOfMoleToAppear].isAppeared = true
            moles.postValue(_moles.toList())
        }
    }

    fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INC)
    }
}