package com.andria.myparticleemitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andria.myparticleemitter.ParticleHelper.toPx
import com.andria.myparticleemitter.ui.theme.MyParticleEmitterTheme
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyParticleEmitterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ParicleSystem(
                        particles = 40,
                        variation = Variation.RIGHT_LEFT_FAN)
                }
            }
        }
    }

    private fun particleAngleForFan(variation: Variation): Double {
        var multiplier = 1.0

        if (Random.nextInt(Integer.MAX_VALUE) % 100 < 50) {
            multiplier = -1.0
        }
        
        val result = (80.0 + Random.nextInt() % 20) * multiplier
        return result
    }

    enum class Variation {
        RIGHT_LEFT_FAN,
        RIGHT_FAN
    }

    @Composable
    fun ParicleSystem(
        particles: Int,
        variation: Variation
    ) {
        val sizeDp = 300.dp
        val sizePx = sizeDp.toPx()
        val sizePxHalf = sizePx / 2
        val infiniteTransition = rememberInfiniteTransition(label = "infinite particles ")

        for (i in 0..particles) {

            //properties of particles

            //calculate random angle for particle based on variation
            val particleAngleForFan = particleAngleForFan(variation)
            val color = Color(Random.nextInt(), Random.nextInt(), Random.nextInt())
            val trajectory = Random.nextInt(100, 600)
            val speed = Random.nextInt(900, 3000)
            val diameter = Random.nextInt(5, 25)

            val xStart = sizePxHalf
            val yStart = sizePxHalf

            val xEnd = xStart + (trajectory * sin(particleAngleForFan)).toFloat()
            val yEnd = yStart + (trajectory * cos(particleAngleForFan)).toFloat()

            //transition animation for x and y
            val particlexvalue by infiniteTransition.animateFloat(
                initialValue = xStart,
                targetValue = xEnd,
                animationSpec = infiniteRepeatable(
                    animation = tween(speed, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )
            val particleyvalue by infiniteTransition.animateFloat(
                initialValue = yStart,
                targetValue = yEnd,
                animationSpec = infiniteRepeatable(
                    animation = tween(speed, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = ""
            )


            Canvas(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0x26000000))
                    .size(sizeDp)
            ) {
                //optional axis
//                drawLine(
//                    color = Color.Black,
//                    start = Offset(sizePxHalf, 0f),
//                    end = Offset(sizePxHalf, sizePx),
//                    strokeWidth = 2.dp.toPx()
//                )
//                drawLine(
//                    color = Color.Black,
//                    start = Offset(0f, sizePxHalf),
//                    end = Offset(sizePx, sizePxHalf),
//                    strokeWidth = 2.dp.toPx()
//                )
                val test = particlexvalue/1000
                
                val result = if(test < 1 && test > 0 ){
                    test
                } else {
                    1f
                }


                drawCircle(
                    color = color,
                    radius = diameter.toFloat(),
                    alpha = result,
                    center = Offset(particlexvalue, particleyvalue)
                )

                drawRect(
                    Color.Black,
                    Offset(sizePxHalf - 50, sizePxHalf - 50),
                    size = Size(100f, 100f),
                )
            }
        }
    }

    @Preview
    @Composable
    fun PreviewParticleSystem() {
        ParicleSystem(
            particles = 50,
            variation = Variation.RIGHT_FAN)
    }
}