package twocheg.mod.ui.impl

import net.minecraft.client.gui.DrawContext
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import twocheg.mod.api.render.HorizontalContainerArea
import twocheg.mod.utils.math.Delta

class SearchArea(
    val onStart: () -> Unit,
    val onQ: (String) -> Unit,
    val onEnd: () -> Unit
) : HorizontalContainerArea(0f) {
    var q = ""

    var inputting = false
    var delta by Delta(this::inputting)

    override fun render(
        context: DrawContext,
        matrices: Matrix4f,
        mouseX: Double,
        mouseY: Double
    ) {
        // TODO
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (isHovered(mouseX, mouseY)) {
            inputting = true
            q = ""
            onStart()
            return true
        } else if (inputting) {
            inputting = false
            return true
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun charTyped(chr: Char, modifiers: Int): Boolean {
        if (inputting) {
            q += chr
            return true
        }
        return super.charTyped(chr, modifiers)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (inputting) {
            if (keyCode in listOf(GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_DELETE)) {
                inputting = false
                onEnd()
            }
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                q.dropLast(1)
                return true
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }
}