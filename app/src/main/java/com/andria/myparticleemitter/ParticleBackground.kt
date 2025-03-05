package com.andria.myparticleemitter

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import com.andria.myparticleemitter.ui.theme.MyParticleEmitterTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ParticleBackground(
    particleCount: Int,
    variation: Variation,
    style: ParticleStyle,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // Draw the particle system as the background.
        ParticleSystem(
            particleCount = particleCount,
            variation = variation,
            particleStyle = style,
            modifier = Modifier.matchParentSize()
        )
        // Overlay the content in its own Box that fills the parent and centers its content.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun ParticleSystem(
    particleCount: Int,
    variation: Variation,
    particleStyle: ParticleStyle = ParticleStyle.Default,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var canvasSizePx by remember { mutableStateOf(0f) }

    // Wrap in a Box to capture the available size.
    Box(
        modifier = modifier.onSizeChanged { size ->
            // Assume a square canvas.
            canvasSizePx = size.width.toFloat()
        }
    ) {
        if (canvasSizePx > 0f) {
            val center = Offset(canvasSizePx / 2, canvasSizePx / 2)

            // Precompute particle properties once, using different ranges for Sparks.
            val particles = remember(particleCount, variation, canvasSizePx, particleStyle) {
                List(particleCount) {
                    Particle(
                        angle = ParticleHelper.particleAngleForFan(variation),
                        color = particleStyle.randomColor(),
                        trajectory = if (particleStyle is ParticleStyle.Sparks)
                            Random.nextInt((canvasSizePx / 4).toInt(), (canvasSizePx / 3).toInt()).toFloat()
                        else
                            Random.nextInt((canvasSizePx / 3).toInt(), (canvasSizePx * 0.9f).toInt()).toFloat(),
                        speed = if (particleStyle is ParticleStyle.Sparks)
                            Random.nextInt(500, 600)
                        else
                            Random.nextInt(900, 3000),
                        diameter = if (particleStyle is ParticleStyle.Sparks)
                            Random.nextInt(3, 9).toFloat()
                        else
                            Random.nextInt(5, 25).toFloat(),
                        xStart = center.x,
                        yStart = center.y
                    )
                }
            }

            // Create an infinite transition for the animations.
            val infiniteTransition = rememberInfiniteTransition()

            // Animate positions for each particle.
            val animatedParticles = particles.map { particle ->
                // Convert angle from degrees to radians.
                val angleRad = Math.toRadians(particle.angle)
                val xPosition by infiniteTransition.animateFloat(
                    initialValue = particle.xStart,
                    targetValue = particle.xStart + (particle.trajectory * sin(angleRad)).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(particle.speed, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                val yPosition by infiniteTransition.animateFloat(
                    initialValue = particle.yStart,
                    targetValue = particle.yStart + (particle.trajectory * cos(angleRad)).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(particle.speed, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                particle.copy(xStart = xPosition, yStart = yPosition)
            }

            // Draw all particles on a Canvas that fills the available space.
            Canvas(modifier = Modifier.matchParentSize().border(1.dp, Color(0x26000000))) {
                animatedParticles.forEach { particle ->
                    // Optionally compute alpha based on the x-position relative to canvas width.
                    val alpha = ((particle.xStart / canvasSizePx).coerceIn(0f, 1f))
                    drawCircle(
                        color = particle.color,
                        radius = particle.diameter,
                        alpha = alpha,
                        center = Offset(particle.xStart, particle.yStart)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewParticleBackground() {
    MyParticleEmitterTheme {
        Surface(
            modifier = Modifier.size(450.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            ParticleBackground(
                particleCount = 50,
                variation = Variation.FULL_CIRCLE,
                style = ParticleStyle.Default
            ) {
                // Centered text content over the particle background.
                Text(
                    text = "Hello, Particle Background!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}