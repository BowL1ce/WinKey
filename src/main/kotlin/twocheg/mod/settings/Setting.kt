package twocheg.mod.settings

import twocheg.mod.managers.ConfigManager
import twocheg.mod.utils.humanizeEnumClassName

open class Setting<T>(
    open val name: String,
    open val defaultValue: T,
    val min: T? = null,
    val max: T? = null,
    private val options: List<T>? = null,
    open val visibility: (T) -> Boolean = { true },
    private val onChange: (T) -> Unit = {}
) {
    var parentGroup: Setting<*>? = null
        internal set

    var config: ConfigManager? = null
        private set

    private var _value: T = defaultValue
    private var _index: Int = options?.indexOf(defaultValue) ?: -1

    init {
        require(options == null || defaultValue in options) {
            "default value must be in options list"
        }
    }

    open var value: T
        get() = if (isList) options!![index] else _value
        set(newValue) {
            if (isList) {
                val idx = options!!.indexOf(newValue)
                if (idx != -1) index = idx
            } else {
                if (_value != newValue) {
                    _value = newValue
                    saveToConfig()
                    onChange(newValue)
                }
            }
        }

    var index: Int
        get() = if (isList) _index.coerceIn(0 until options!!.size) else -1
        set(newIndex) {
            if (!isList) return
            val clamped = newIndex.coerceIn(0 until options!!.size)
            if (_index != clamped) {
                _index = clamped
                saveToConfig()
                onChange(options[clamped])
            }
        }

    val isList: Boolean get() = options != null
    val isNumber: Boolean get() = min != null && max != null
    val isBoolean: Boolean get() = defaultValue is Boolean
    val isString: Boolean get() = defaultValue is String

    fun getOptions(): List<T>? = options

    fun isVisible(): Boolean = visibility(value)

    fun getGroup(): Setting<*>? = parentGroup

    fun resetToDefault() {
        value = defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    fun setAny(v: Any?) {
        value = v as T
    }

    fun init(config: ConfigManager) {
        this.config = config
        val keyPath = if (parentGroup != null) {
            config.keyPath + ".${parentGroup!!.name}"
        } else config.keyPath

        val saved = config.get(name, if (isList) _index else _value)

        if (isList) {
            @Suppress("UNCHECKED_CAST")
            index = saved as Int
        } else {
            @Suppress("UNCHECKED_CAST")
            value = saved as T
        }
    }

    private fun saveToConfig() {
        config?.let {
            if (isList) it.set(name, index) else it.set(name, _value)
        }
    }

    companion object {
        @JvmStatic
        inline fun <reified E : Enum<E>> fromEnum(defaultValue: E): Setting<E> {
            val name = humanizeEnumClassName(defaultValue::class.java)
            val options = enumValues<E>().toList()
            return Setting(name, defaultValue, options = options)
        }

        @JvmStatic
        fun <T> fromOptions(defaultValue: T, name: String, vararg options: T): Setting<T> {
            return Setting(name, defaultValue, options = options.toList())
        }

        @JvmStatic
        fun <T> fromRange(name: String, defaultValue: T, min: T, max: T): Setting<T> {
            return Setting(name, defaultValue, min = min, max = max)
        }
    }
}