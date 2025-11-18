package twocheg.mod.api.settings

import twocheg.mod.managers.ConfigManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SettingBase<T>(
    override val name: String,
    override val default: T,
) : Setting<T>, ReadWriteProperty<Any?, T> {
    var visibility: () -> Boolean = { true }

    private lateinit var config: ConfigManager

    override var value: T = default
        set(v) {
            config[name] = v
            field = v
        }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun reset() {
        value = default
    }

    override fun get(): T {
        return value
    }

    override fun set(value: T) {
        this.value = value
    }

    override fun init(config: ConfigManager) {
        // да, согласен, немного кончено, но мне похуй
        this.config = config.sub(name)
        value = this.config[name, default]
    }
}
