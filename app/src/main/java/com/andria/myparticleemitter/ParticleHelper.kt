package com.andria.myparticleemitter

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import kotlin.random.Random

object ParticleHelper {
    /** Helper to generate a random angle based on the variation. */
    fun particleAngleForFan(variation: Variation): Double {
        return when (variation) {
            Variation.FULL_CIRCLE -> Random.nextDouble(0.0, 360.0)
            else -> {
                val multiplier = if (Random.nextInt(100) < 50) -1.0 else 1.0
                (80.0 + Random.nextInt(20)) * multiplier
            }
        }
    }

    fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density
    fun Dp.toPx() = value.dpToPx()
}