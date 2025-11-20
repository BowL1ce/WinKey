package twocheg.mod.api.modules

import org.lwjgl.glfw.GLFW
import twocheg.mod.Categories
import twocheg.mod.api.settings.BooleanSetting
import twocheg.mod.api.settings.ListSettings

class Example : Parent(
    name = "example",
    description = "example module",
    category = Categories.example,
    enabledByDefault = false,
    disableOnStartup = false,
    defaultKeyBind = GLFW.GLFW_KEY_R
) {
    val bool by BooleanSetting("boolean", false)
    val list by ListSettings("list", 1, 2, 3)
}