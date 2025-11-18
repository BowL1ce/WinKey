package twocheg.mod.api.settings

import twocheg.mod.managers.ConfigManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ListSettings<T>(
    override val name: String,
    vararg values: T
) : Setting<T>, ReadWriteProperty<Any?, T> {
    val values = values.toList()

    private lateinit var config: ConfigManager

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

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
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

    @Suppress("UNCHECKED_CAST")
    fun setAny(v: Any?) {
        value = v as T
    }

    override fun reset() {
        value = values.first()
    }

    override fun get(): T {
        return value
    }

    override fun set(value: T) {
        this.value = value
    }

    override fun init(config: ConfigManager) {
        this.config = config.sub(name)
        index = this.config[name, defaultIndex]
    }
}
