package com.andria.myparticleemitter

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

sealed class ParticleStyle {
    object Default : ParticleStyle()
    object MSBrand : ParticleStyle() // random shades of green and white
    object Sparks : ParticleStyle()  // golden and yellow sparks

    fun randomColor(random: Random = Random): Color {
        return when (this) {
            Default -> Color(
                red = random.nextInt(256),
                green = random.nextInt(256),
                blue = random.nextInt(256)
            )
            MSBrand -> {
                if (random.nextBoolean()) {
                    // Random green shade
                    Color(
                        red = random.nextInt(0, 50),
                        green = random.nextInt(150, 256),
                        blue = random.nextInt(0, 50)
                    )
                } else {
                    // Light white variant
                    Color(
                        red = random.nextInt(200, 256),
                        green = random.nextInt(200, 256),
                        blue = random.nextInt(200, 256)
                    )
                }
            }
            Sparks -> {
                // Golden/yellow spark colors: high red, high green, low blue.
                Color(
                    red = random.nextInt(200, 256),
                    green = random.nextInt(150, 256),
                    blue = random.nextInt(0, 50)
                )
            }
        }
    }
}