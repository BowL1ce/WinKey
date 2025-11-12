package twocheg.mod.utils.math

import java.util.concurrent.ThreadLocalRandom

fun random(min: Double, max: Double): Double {
    return ThreadLocalRandom.current().nextDouble() * (max - min) + min
}

fun random(min: Float, max: Float): Float {
    return (Math.random() * (max - min) + min).toFloat()
}

fun random(min: Int, max: Int): Int {
    return (Math.random() * (max - min) + min).toInt()
}