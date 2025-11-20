package twocheg.mod.api.settings

import twocheg.mod.api.modules.Parent
import twocheg.mod.managers.ConfigManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ListSettings<T>(
    override val name: String,
    vararg values: T
) : Setting<T>, ReadWriteProperty<Parent, T> {
    val values = values.toList()

    override lateinit var config: ConfigManager

    override val default: T = values.first()
    val defaultIndex = 0

    var index = defaultIndex
        set(v) {
            config[name] = v
            field = v
        }
    override var value: T = values.first()
        get() = values[index]
        set(v) {
            index = values.indexOf(v)
            field = v
        }

    override fun getValue(thisRef: Parent, property: KProperty<*>): T {
        if (!this::config.isInitialized) thisRef.registerSetting(this)
        return value
    }

    override fun setValue(thisRef: Parent, property: KProperty<*>, value: T) {
        if (!this::config.isInitialized) thisRef.registerSetting(this)
        this.value = value
    }

    fun next(): T {
        var i = index + 1
        if (i > values.size - 1) i = 0
        else if (i < 0) i = values.size - 1
        index = i
        return value
    }

    fun prev(): T {
        var i = index - 1
        if (i > values.size - 1) i = 0
        else if (i < 0) i = values.size - 1
        index = i
        return value
    }

    override fun reset() {
        value = values.first()
    }

    override fun init(config: ConfigManager) {
        this.config = config.sub(name)
        index = this.config[name, defaultIndex]
    }
}
