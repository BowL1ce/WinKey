package twocheg.mod.settings

class Header(
    title: String,
    onClick: (Int) -> Unit = {},
    visibility: (Int) -> Boolean = { true }
) : Setting<Int>(title, 0, onChange = onClick, visibility = visibility)