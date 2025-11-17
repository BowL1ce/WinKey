package twocheg.mod.modules.client

import org.lwjgl.glfw.GLFW
import twocheg.mod.Categories
import twocheg.mod.managers.ModuleManager
import twocheg.mod.screens.ConfigsScreen
import twocheg.mod.screens.ModulesScreen
import twocheg.mod.screens.ScreenBase
import twocheg.mod.screens.impl.modules.ScreensArea
import twocheg.mod.modules.Parent
import twocheg.mod.screens.impl.modules.CategoryArea
import twocheg.mod.utils.math.Delta


class ClickGui : Parent(
    "click gui",
    "screens handler",
    Categories.client,
    disableOnStartup = true,
    defaultKeyBind = GLFW.GLFW_KEY_RIGHT_SHIFT
) {
    val openFactor = Delta(this::enable)

    override var keybind: Int = config["keybind", defaultKeyBind]
        set(k) {
            if (k != -1) { // ему нельзя удалить клавишу
                config["keybind"] = k
                field = k
            }
        }

    var selectScreens = ScreensArea(ModulesScreen::class.java, ConfigsScreen::class.java)
    val categories = mutableListOf<CategoryArea>()

    init {
        this.selectScreens.showFactor = openFactor
    }

    override fun onDisable() {
        mc.currentScreen?.let { screen ->
            if (screen is ScreenBase) {
                screen.close()
            }
        }
    }

    override fun onEnable() {
        openFactor.reset() // я не знаю, дельта накапливает значение до 1 и ему арифметически и кристально поебать, потом исправлю, когда будет не похуй (никогда)
        if (categories.isEmpty()) {
            for ((category, moduleList) in ModuleManager.byCategory) {
                categories.add(CategoryArea(category, moduleList))
            }
        }
        resetComponents()
        selectScreens.reset()
        mc.setScreen(selectScreens.createGui(selectScreens.defaultGuiClass))
    }

    fun resetComponents() {
        for (category in categories) {
            category.reset()
        }
    }
}