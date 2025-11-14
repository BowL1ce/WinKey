package twocheg.mod.settings

class Header(
    title: String,
    onClick: (Int) -> Unit = {},
    override val visibility: (Int) -> Boolean = { true }
) : Setting<Int>(title, 0, onChange = onClick, visibility = visibility)