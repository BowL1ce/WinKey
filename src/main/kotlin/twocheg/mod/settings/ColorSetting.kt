package twocheg.mod.settings

import java.awt.Color

class ColorSetting(
    name: String,
    defaultValue: Int,
    val hasAlpha: Boolean = true
) : Setting<Int>(name, defaultValue) {
    val color: Color
        get() {
            val argb = getValue()
            return if (hasAlpha) {
                Color(argb, true)
            } else {
                Color(
                    (argb shr 16) and 0xFF,
                    (argb shr 8) and 0xFF,
                    argb and 0xFF
                )
            }
        }

    val r: Int get() = (getValue() shr 16) and 0xFF
    val g: Int get() = (getValue() shr 8) and 0xFF
    val b: Int get() = getValue() and 0xFF
    val a: Int get() = if (hasAlpha) (getValue() shr 24) and 0xFF else 255

    fun setColor(newColor: Color) {
        val rgb = if (hasAlpha) {
            newColor.rgb
        } else {
            newColor.red shl 16 or (newColor.green shl 8) or newColor.blue
        }
        setValue(rgb)
    }

    fun setColor(r: Int, g: Int, b: Int, a: Int = 255) {
        val rgb = if (hasAlpha) {
            (a.coerceIn(0..255) shl 24) or
                    (r.coerceIn(0..255) shl 16) or
                    (g.coerceIn(0..255) shl 8) or
                    (b.coerceIn(0..255))
        } else {
            (r.coerceIn(0..255) shl 16) or
                    (g.coerceIn(0..255) shl 8) or
                    (b.coerceIn(0..255))
        }
        setValue(rgb)
    }
}