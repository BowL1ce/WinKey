package twocheg.mod.utils.math

import kotlin.math.max

val records = mutableListOf<Long>()
var fps = 5

fun recordFrame() {
    val c = System.currentTimeMillis()
    records.add(c)
    records.removeIf { aLong: Long? -> aLong!! + 1000 < System.currentTimeMillis() }
    fps = max(records.size, 4)
}