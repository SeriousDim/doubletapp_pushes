package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext
import kotlin.math.sqrt

interface ILocationPush: IPush {
    var x: Float;
    var y: Float;
    var radius: Int;

    fun getDistanceToUserPosition(): Float {
        val gesture = context.getGestureListenerService().getGesture();
        return sqrt((gesture.x - this.x)*(gesture.x - this.x)
                + (gesture.y - this.y)*(gesture.y - this.y))
    }

    override fun shouldBeFiltered(): Boolean {
        return this.getDistanceToUserPosition() > this.radius
    }
}