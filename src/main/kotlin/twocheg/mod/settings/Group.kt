package twocheg.mod.settings

enum class GroupLayout { VERTICAL, TAB }

class Group(
    override val name: String,
    val settings: List<Setting<*>>,
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
    override val defaultValue: Boolean = false,
    val settings: List<Setting<*>>,
) : Setting<Boolean>(name, defaultValue) {
    init {
        for (setting in settings) {
            setting.parentGroup = this
        }
    }

    val layout: GroupLayout = GroupLayout.VERTICAL
}
