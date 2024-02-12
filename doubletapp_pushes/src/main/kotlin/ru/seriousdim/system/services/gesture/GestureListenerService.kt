package ru.seriousdim.system.services.gesture

import ru.seriousdim.system.services.gesture.model.FingerGesture

class GestureListenerService: IGestureListenerService {
    private val gesture = FingerGesture()

    override fun setX(x: Float) {
        this.gesture.x = x
    }

    override fun setY(y: Float) {
        this.gesture.y = y
    }

    override fun getGesture(): FingerGesture {
        return this.gesture
    }
}