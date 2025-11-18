package twocheg.mod.api.render

import twocheg.mod.utils.math.Delta

abstract class HorizontalContainerArea(
    height: Float,
    override var parent: RenderArea? = null,
    showDelta: Delta? = null
) : ContainerArea(0f, 0f, 0f, height, parent, showDelta) {
    override fun recalculateLayout(availableWidth: Float, availableHeight: Float) {

    }
}