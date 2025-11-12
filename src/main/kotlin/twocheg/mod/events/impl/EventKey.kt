package twocheg.mod.events.impl

import twocheg.mod.events.Event

data class EventKeyPress(val keyCode: Int, val scanCode: Int, val modifiers: Int) : Event()

data class EventKeyRelease(val keyCode: Int, val scanCode: Int, val modifiers: Int) : Event()