package ru.seriousdim.system.model.context

import ru.seriousdim.system.services.gesture.IGestureListenerService
import ru.seriousdim.system.services.push.IPushNotificationService

interface IContext {
    fun getGestureListenerService(): IGestureListenerService

    fun getPushNotificationService(): IPushNotificationService
}