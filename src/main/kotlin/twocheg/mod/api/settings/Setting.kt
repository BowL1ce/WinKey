package twocheg.mod.api.settings

import twocheg.mod.api.modules.Parent
import twocheg.mod.managers.ConfigManager

interface Setting<T> {
    val name: String
    val default: T
    var value: T

    fun reset()
    fun init(config: ConfigManager)

    var config: ConfigManager
}