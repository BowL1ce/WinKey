package twocheg.mod.settings

import java.awt.Color

class ColorSetting(
    override val name: String,
    override val defaultValue: Int,
    val hasAlpha: Boolean = true
) : Setting<Int>(name, defaultValue) {
    fun get(): Color {
        return Color(
            (value shr 16) and 0xFF,
            (value shr 8) and 0xFF,
            value and 0xFF,
            if (hasAlpha) (value shr 24) and 0xFF else 255
        )
    }

    fun set(newColor: Color) {
        value = if (hasAlpha) {
            newColor.rgb
        } else {
            newColor.red shl 16 or (newColor.green shl 8) or newColor.blue
        }
    }

    fun set(r: Int, g: Int, b: Int, a: Int = 255) {
        value = if (hasAlpha) {
            (a.coerceIn(0..255) shl 24) or
                    (r.coerceIn(0..255) shl 16) or
                    (g.coerceIn(0..255) shl 8) or
                    (b.coerceIn(0..255))
        } else {
            (r.coerceIn(0..255) shl 16) or
                    (g.coerceIn(0..255) shl 8) or
                    (b.coerceIn(0..255))
        }
    }
}