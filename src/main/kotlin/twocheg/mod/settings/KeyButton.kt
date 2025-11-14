package twocheg.mod.settings

import meteordevelopment.orbit.EventHandler
import twocheg.mod.EVENT_BUS
import twocheg.mod.events.impl.EventKeyPress

class KeyButton(
    override val name: String,
    keyCode: Int,
    val onClick: (KeyButton) -> Unit = {}
) : Setting<Int>(name, keyCode) {
    init {
        EVENT_BUS.subscribe(this)
    }

    @EventHandler
    fun onKey(e: EventKeyPress) {
        if (e.keyCode == value)
            onClick(this)
    }
}