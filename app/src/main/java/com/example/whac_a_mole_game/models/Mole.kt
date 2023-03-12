package com.example.whac_a_mole_game.models

import androidx.annotation.DrawableRes
import com.example.whac_a_mole_game.R

data class Mole(
    val id: Int,
    var isAppeared: Boolean = false,
    @DrawableRes
    val image: Int = R.drawable.mole,
)