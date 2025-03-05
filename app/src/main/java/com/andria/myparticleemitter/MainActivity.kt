package com.andria.myparticleemitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andria.myparticleemitter.ui.theme.MyParticleEmitterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
    }
}