# Particle Emitter

This is a **particle emitter** application built with Kotlin and Android Jetpack Compose. It creates a dynamic background of particles with various customizable properties.

## Demo

Check out this animated demo:

![particleemitterdemo-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/7c229d03-26c9-4812-957a-d9ac3878c5dd)

## Features

- Dynamic particle system using Jetpack Compose
- Multiple emission patterns: right-left fan, right fan, full circle
- Customizable themes (random, brand-specific, sparks)

- ## Configurable Parameters

The particle emitter is designed to be flexible. Here are the key configurable parameters:

1. **Particle Count (`particleCount`)**
   - **Purpose:** Determines the number of particles generated.
   - **Usage:** Increase this number for a denser effect.
   - **Example:** `particleCount = 50` creates 50 particles.

2. **Emission Variation (`variation`)**
   - **Purpose:** Controls the spread/direction of the particles.
   - **Variants:**
     - **RIGHT_LEFT_FAN:** Particles spread in a fan-like pattern, alternating left and right.
     - **RIGHT_FAN:** Particles spread only to the right.
     - **FULL_CIRCLE:** Particles emit uniformly in all directions (0°–360°).

3. **Particle Style (`particleStyle`)**
   - **Purpose:** Determines the color theme of the particles.
   - **Styles:**
     - **Default/Random:** Completely random colors.
     - **MSBrand:** Colors inspired by the m&S branding (random shades of green and near white).
     - **Sparks:** Golden/yellow hues for a spark-like effect.

4. **Canvas Size & Dynamic Scaling**
   - **Purpose:** Automatically adapts the particle system to the available space.
   - **Usage:** Uses `onSizeChanged` or `BoxWithConstraints` to measure the container and calculate the center dynamically, ensuring that the emitter remains centered regardless of device or screen size.
   - **Customizability:** Modify the `modifier` in the `ParticleBackground` to control the overall size.

5. **Particle Motion Properties**
   - **Trajectory:** Controls how far each particle travels from the center.
   - **Speed:** Determines the duration of each particle's animation cycle.
   - **Diameter:** Sets the size of each particle.
   - **Usage:** These properties are randomized within ranges that are proportional to the canvas size. For styles like *Sparks*, these ranges are adjusted to create a more energetic effect (shorter trajectories, faster speeds, and smaller particles).

6. **Background Integration (`ParticleBackground`)**
   - **Purpose:** Wraps the particle system as a dynamic background for other UI elements.
   - **Usage:** Allows you to overlay content (e.g., centered text) on top of the animated particles.
   - **Customizability:** Use modifiers to adjust the size and alignment of the background container.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/AndriaNjoku/Particle-Emitter-.git
