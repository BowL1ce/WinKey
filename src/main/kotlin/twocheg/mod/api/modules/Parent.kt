package twocheg.mod.api.modules

import net.minecraft.client.MinecraftClient
import twocheg.mod.Categories
import twocheg.mod.managers.ConfigManager
import twocheg.mod.managers.ModuleManager
import twocheg.mod.api.settings.Setting

abstract class Parent(
    override val name: String,
    override val description: String? = null,
    val category: Categories,
    val enabledByDefault: Boolean = false,
    val disableOnStartup: Boolean = false,
    val visibleInUI: Boolean = true,
    var defaultKeyBind: Int = -1
) : Module {
    companion object {
        @JvmStatic
        val mc: MinecraftClient = MinecraftClient.getInstance()

        @JvmStatic
        fun fullNullCheck(): Boolean = mc.player == null || mc.world == null
    }

    val settings = mutableListOf<Setting<*>>()

    val config = ConfigManager("modules.$name")

    override var enable: Boolean = if (disableOnStartup) false else config["enabled", enabledByDefault]
        set(e) {
            config["enable"] = e
            field = e
            if (field) onEnable()
            else onDisable()
        }
    override var keybind: Int = config["keybind", defaultKeyBind]
        set(k) {
            config["keybind"] = k
            field = k
        }

    open fun onEnable() {}
    open fun onDisable() {}
    open fun onToggle() {}

     override fun toggle() {
        enable = !enable
        onToggle()
    }

    fun resetToDefault() {
        enable = enabledByDefault
        keybind = defaultKeyBind
    }

    fun <T : Setting<*>> registerSetting(setting: T): T {
        setting.init(config)
        settings.add(setting)
        return setting
    }

    init {
        ModuleManager.register(this)
    }
}

