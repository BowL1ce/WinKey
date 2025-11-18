package twocheg.mod.ui.impl

import com.google.common.base.Supplier
import net.minecraft.client.gui.DrawContext
import org.joml.Matrix4f
import twocheg.mod.api.render.ContainerArea
import twocheg.mod.api.render.RenderArea
import twocheg.mod.builders.Builder
import twocheg.mod.builders.states.QuadColorState
import twocheg.mod.builders.states.QuadRadiusState
import twocheg.mod.builders.states.SizeState
import twocheg.mod.utils.math.ColorUtils.fromRGB
import twocheg.mod.utils.math.Spring

class SelectionArea() : ContainerArea() {
    var targetArea: () -> RenderArea? = { null }

    private var targetX by Spring(0f)
    private var targetY by Spring(0f)
    private var targetWidth by Spring(0f)
    private var targetHeight by Spring(0f)

    override fun render(
        context: DrawContext,
        matrices: Matrix4f,
        mouseX: Double,
        mouseY: Double
    ) {
        targetX = x
        targetY = y
        targetWidth = width
        targetHeight = height

        // println("x = $x | y = $y | width = $width | height = $height")

        val rectangle = Builder.rectangle()
            .size(SizeState(targetWidth, targetHeight))
            .color(QuadColorState(fromRGB(0, 0, 0, 50 * showFactor)))
            .radius(QuadRadiusState(6f))
            .build()
        rectangle.render(matrices, targetX, targetY)
        Builder.border()
            .size(rectangle.size)
            .color(QuadColorState(fromRGB(255, 255, 255, 18 * showFactor)))
            .radius(rectangle.radius)
            .thickness(0.1f)
            .build()
            .render(matrices, targetX, targetY)
    }

    override fun recalculateLayout(availableWidth: Float, availableHeight: Float) {
        val targetArea = targetArea()
        println(targetArea)
        if (targetArea != null) {
            x = targetArea.x
            y = targetArea.y
            width = targetArea.width
            height = targetArea.height
        }
    }
}