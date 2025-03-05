package com.andria.myparticleemitter

import androidx.compose.ui.graphics.Color

/** Data class representing a single particle's properties. */
data class Particle(
    val angle: Double,
    val color: Color,
    val trajectory: Float,
    val speed: Int,
    val diameter: Float,
    val xStart: Float,
    val yStart: Float
)