// представляю, новый, крутой, удобный, полезный инструмент, мечта любого дизайнера читов на майнкрафт (да да это чистая правда)
// автоматический расчитыватель значений 3000 инатор редми т ксяоми 21 про, сокращенно крутые классы для анимаций
package twocheg.mod.utils.math

import kotlin.math.*

enum class CurveType {
    EaseIn,
    EaseOut,
    EaseInOut,
    Linear,
    Pulse;

    fun get(t: Float): Float {
        return when (this) {
            Linear -> t
            EaseIn -> t * t
            EaseOut -> 1f - (1f - t) * (1f - t)
            EaseInOut -> if (t < 0.5f) {
                2f * t * t
            } else {
                1f - (-2f * t + 2f).pow(2f) / 2f
            }
            Pulse -> -1f // рассчитываться в другом месте
        }
    }
}

class Delta(
    val directionProvider: () -> Boolean,
    val durationMs: Long = 400,
    val mode: CurveType = CurveType.EaseInOut,
    val parentFactor: () -> Float = { 1f }
) {
    private var lastUpdateTime: Long = 0
    private var accumulatedTime: Float = 0f
    private var targetDirection: Boolean = true

    init {
        reset()
    }

    fun reset() {
        accumulatedTime = 0f
        lastUpdateTime = System.nanoTime()
        targetDirection = directionProvider()
    }

    fun setProgress(progress: Float) {
        accumulatedTime = (progress.coerceIn(0f, 1f) * durationMs).coerceIn(0f, durationMs.toFloat())
        lastUpdateTime = System.nanoTime()
        targetDirection = directionProvider()
    }

    fun get(): Float {
        val now = System.nanoTime()
        val deltaMs = (now - lastUpdateTime) / 1_000_000f
        lastUpdateTime = now

        return (if (mode == CurveType.Pulse && directionProvider()) {
            accumulatedTime += deltaMs
            calculateSinePulseProgress()
        } else {
            accumulatedTime += if (targetDirection) deltaMs else -deltaMs
            accumulatedTime = accumulatedTime.coerceIn(0f, durationMs.toFloat())
            checkDirectionChange()
            calculateProgress()
        }) * parentFactor()
    }

    private fun checkDirectionChange() {
        val desiredDirection = directionProvider()
        if (desiredDirection != targetDirection) {
            targetDirection = desiredDirection
            lastUpdateTime = System.nanoTime()
        }
    }

    private fun calculateProgress(): Float {
        if (durationMs <= 0) return 1f
        val linearProgress = accumulatedTime / durationMs
        return mode.get(linearProgress).coerceIn(0f, 1f)
    }

    private fun calculateSinePulseProgress(): Float {
        if (durationMs <= 0) return 0f
        val phase = (accumulatedTime % durationMs) / durationMs
        val angle = PI * phase
        return sin(angle).toFloat().coerceIn(0f, 1f)
    }
}

class Lerp<T : Number>(
    initialValue: T,
    durationMs: Long = 400,
    mode: CurveType = CurveType.EaseInOut
) {
    var currentValue = initialValue
    var targetValue = initialValue
    private val delta = Delta({ true }, durationMs, mode)

    fun set(newTarget: T) {
        if (newTarget == currentValue) return
        targetValue = newTarget
        delta.reset()
    }

    fun forceSet(newTarget: T) {
        currentValue = newTarget
        targetValue = newTarget
    }

    fun get(): T {
        currentValue = lerp(currentValue, targetValue, delta.get())
        return currentValue
    }

    @Suppress("UNCHECKED_CAST")
    private fun lerp(start: Number, end: Number, t: Float): T {
        return (start.toFloat() + (end.toFloat() - start.toFloat()) * t) as T
    }
}
