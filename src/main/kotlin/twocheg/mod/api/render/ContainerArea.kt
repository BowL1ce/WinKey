package twocheg.mod.api.render

import net.minecraft.client.MinecraftClient
import twocheg.mod.ui.impl.ValueArea
import twocheg.mod.utils.math.Delta

abstract class ContainerArea(
    override var x: Float = 0f,
    override var y: Float = 0f,
    override var width: Float = 0f,
    override var height: Float = 0f,
    override var parent: RenderArea? = null,
) : RenderArea {
    override var show: Boolean = true

    private var _showFactor = Delta(this::show)
    override var showFactor: Float
        get() = _showFactor.get() * (parent?.showFactor ?: 1f)
        set(value) { _showFactor.set(value) }

    protected val children = mutableListOf<RenderArea>()

    fun addChild(child: RenderArea) {
        children += child
        child.parent = this
    }

    fun removeChild(child: RenderArea) {
        children -= child
        child.parent = null
    }

    fun clearChildren() {
        children.forEach { it.parent = null }
        children.clear()
    }

    inline fun <reified T : RenderArea> findChild(children: List<RenderArea>): T? {
        for (area in children) {
            if (area is T) return area
        }
        return null
    }

    fun <T> findChild(value: T): ValueArea<T>? {
        for (child in this.children) {
            if (child is ValueArea<*>) {
                if (child.value == value) {
                    return child as ValueArea<T>
                }
            }
        }
        return null
    }

    override fun recalculateLayout(availableWidth: Float, availableHeight: Float) {
        // при сталине такой хуйни не было
        // раньше без этого как то справлялись, прям в функции рендера ебашили все что можно и норм
    }

    protected inline fun propagateToChildrenReversed(block: RenderArea.() -> Boolean): Boolean {
        for (i in children.indices.reversed()) {
            if (children[i].block()) return true
        }
        return false
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) =
        propagateToChildrenReversed { mouseClicked(mouseX, mouseY, button) }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) =
        propagateToChildrenReversed { mouseReleased(mouseX, mouseY, button) }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double) =
        propagateToChildrenReversed { mouseDragged(mouseX, mouseY, button, deltaX, deltaY) }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double) =
        propagateToChildrenReversed { mouseScrolled(mouseX, mouseY, scrollX, scrollY) }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int) =
        propagateToChildrenReversed { keyPressed(keyCode, scanCode, modifiers) }

    override fun charTyped(chr: Char, modifiers: Int) =
        propagateToChildrenReversed { charTyped(chr, modifiers) }

    fun getShowDelta() = _showFactor

    fun setShowDelta(delta: Delta) {
        _showFactor = delta
    }

    companion object {
        val mc: MinecraftClient = MinecraftClient.getInstance()

        const val PADDING = 4.5f
    }
}