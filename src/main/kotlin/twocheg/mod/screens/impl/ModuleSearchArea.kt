package twocheg.mod.screens.impl

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import twocheg.mod.builders.Builder
import twocheg.mod.builders.states.QuadColorState
import twocheg.mod.builders.states.SizeState
import twocheg.mod.identifier
import twocheg.mod.utils.math.fromRGB


class ModuleSearchArea(override val parentArea: RenderArea, val onInter: () -> Unit) : RenderArea(parentArea) {
    var isActive = false


    init {
        this.width = 14 + PADDING * 2
        this.height = 14 + PADDING * 2
    }

    override fun render(
        context: DrawContext,
        matrix: Matrix4f,
        x: Float,
        y: Float,
        width: Float?,
        height: Float?,
        mouseX: Double,
        mouseY: Double
    ) {
        val abstractTexture: AbstractTexture = mc.textureManager.getTexture(identifier("textures/ui/search.png"))
        Builder.texture()
            .size(SizeState(this.width, this.height))
            .texture(0f, 0f, 1f, 1f, abstractTexture)
            .color(QuadColorState(fromRGB(255, 255, 255, 200 * showFactor.get())))
            .build()
            .render(matrix, x, y)
        super.render(context, matrix, x, y, this.width, this.height, mouseX, mouseY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (isHovered(mouseX, mouseY)) {
            onInter()
            isActive = true
            return true
        } else if (isActive) {
            isActive = false
            onInter()
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (isActive && keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onInter()
            isActive = false
            return false
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }
}