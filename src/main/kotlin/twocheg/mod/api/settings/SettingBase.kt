package twocheg.mod.api.settings

import twocheg.mod.api.modules.Parent
import twocheg.mod.managers.ConfigManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SettingBase<T>(
    override val name: String,
    override val default: T,
) : Setting<T>, ReadWriteProperty<Parent, T> {
    override lateinit var config: ConfigManager

    override var value: T = default

    override fun getValue(thisRef: Parent, property: KProperty<*>): T {
        if (!this::config.isInitialized) thisRef.registerSetting(this)
        return value
    }

    override fun setValue(thisRef: Parent, property: KProperty<*>, value: T) {
        if (!this::config.isInitialized) thisRef.registerSetting(this)
        this.value = value
    }

    override fun reset() {
        value = default
    }

    override fun init(config: ConfigManager) {
        this.config = config.sub(name)
        value = config[name, default]
    }
}
