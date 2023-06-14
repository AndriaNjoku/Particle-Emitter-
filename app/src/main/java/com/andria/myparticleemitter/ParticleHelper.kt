package com.andria.myparticleemitter

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import kotlin.random.Random

object ParticleHelper {


    fun particleAngleForFan(): Double {
        //we want our particles to fan out from the left and right hand side to avoid clipping the top and bottom (which looks awful)
        var multiplier = 1.0

        val generateRandom = Random.nextInt(0, Integer.MAX_VALUE)


        // Generate random value
        if ( generateRandom % 100 < 50) {
            multiplier = -1.0
        }
        return (80.0 + (generateRandom % 20).toDouble() * multiplier)

    }

    fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density
    fun Dp.toPx() = value.dpToPx()
}