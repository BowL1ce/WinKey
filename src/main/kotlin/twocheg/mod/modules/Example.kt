package twocheg.mod.modules

import twocheg.mod.Categories
import twocheg.mod.settings.Group
import twocheg.mod.settings.Header
import twocheg.mod.settings.Setting

class Example : Parent("example", Categories.example) {
    var header: Header = Header("header")
    var intSetting: Setting<Float> = Setting("int", 3.0f, 1.0f, 6.0f)
    var boolSetting: Setting<Boolean> = Setting("bool", true)
    var strSetting: Setting<String> = Setting("str", "your text")
    var list: Setting<Int> = Setting(
        "list", 1,
        options = listOf(1, 2, 3)
    )
    var header2: Header = Header("настойки типо")
    var list2: Setting<String> = Setting(
        "list", "1",
        options = listOf("1", "2", "3")
    )
    var group: Group = Group(
        "group/category",
        listOf(header2, list2)
    )

    var visible: Setting<Boolean> = Setting("visible", false)
    var header3: Header = Header("ты видишь меня", visibility = {m -> visible.value})
}