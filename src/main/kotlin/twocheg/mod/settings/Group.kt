package twocheg.mod.settings

enum class GroupLayout { VERTICAL, TAB }

class Group(
    override val name: String,
    settings: List<Setting<*>>,
    val layout: GroupLayout = GroupLayout.VERTICAL
) : Setting<Setting<*>>(name, settings[0], options = settings) {
    init {
        for (setting in settings) {
            setting.parentGroup = this
        }
    }
}

class ToggleableGroup(
    override val name: String,
    defaultValue: Boolean = false,
    vararg settings: Setting<*>
) : Setting<Boolean>(name, defaultValue) {
    val childSettings = settings.toList()

    init {
        for (setting in childSettings) {
            setting.parentGroup = this
        }
    }

    val layout: GroupLayout = GroupLayout.VERTICAL
}
