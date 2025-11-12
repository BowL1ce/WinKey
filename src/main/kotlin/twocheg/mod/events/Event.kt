package twocheg.mod.events

abstract class Event {
    private var cancelled = false

    fun isCancelled(): Boolean {
        return cancelled
    }

    fun cancel() {
        cancelled = true
    }
}