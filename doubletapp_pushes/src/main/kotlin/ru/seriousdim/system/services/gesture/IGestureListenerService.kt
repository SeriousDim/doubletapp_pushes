package ru.seriousdim.system.services.gesture

import ru.seriousdim.system.services.gesture.model.FingerGesture

interface IGestureListenerService {
    fun setX(x: Float)

    fun setY(y: Float)

    fun getGesture(): FingerGesture
}