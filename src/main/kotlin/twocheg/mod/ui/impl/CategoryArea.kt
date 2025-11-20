package twocheg.mod.ui.impl

import net.minecraft.client.gui.DrawContext
import org.joml.Matrix4f
import twocheg.mod.Categories
import twocheg.mod.api.modules.Module
import twocheg.mod.api.render.VerticalContainerArea
import twocheg.mod.utils.math.ColorUtils.fromRGB
import twocheg.mod.utils.math.Spring

import dev.hikarian.renderer.Renderer
import dev.hikarian.renderer.math.*
import dev.hikarian.renderer.shapes.*

class CategoryArea(
    val data: Pair<Categories, List<Module>>
) : VerticalContainerArea(0f) {
    var targetHeight by Spring(0f)

    override fun render(
        context: DrawContext,
        matrices: Matrix4f,
        mouseX: Double,
        mouseY: Double
    ) {
        val y = y - 100 * (1 - showFactor)

        Renderer.roundedRect(
            x = x,
            y = y,
            width = width,
            height = targetHeight,
            radius = 10f,
            color = fromRGB(110, 110, 110, 255f * showFactor),
            blur = BlurEffect(radius = 12f)
        )

        Renderer.roundedRectOutline(
            x = x,
            y = y,
            width = width,
            height = targetHeight,
            radius = 10f,
            color = fromRGB(255, 255, 255, 25f * showFactor),
            thickness = 0.2f
        )
    }

    override fun recalculateLayout(availableWidth: Float, availableHeight: Float) {
        height = 100f
        targetHeight = height
    }
}