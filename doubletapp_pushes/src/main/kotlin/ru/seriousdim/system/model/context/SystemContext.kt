package ru.seriousdim.system.model.context

import ru.seriousdim.system.model.user.Gender
import ru.seriousdim.system.model.user.User
import ru.seriousdim.system.services.gesture.GestureListenerService
import ru.seriousdim.system.services.gesture.IGestureListenerService
import ru.seriousdim.system.services.push.IPushNotificationService
import ru.seriousdim.system.services.push.PushNotificationService

open class SystemContext (
    val user: User = User(1, Gender.MALE),
    var time: Long = 0,
    var osVersion: Int = 1
): IContext {
    private val gestureListenerService = GestureListenerService()
    private val pushNotificationService = PushNotificationService()

    override fun getGestureListenerService(): IGestureListenerService {
        return this.gestureListenerService
    }

    override fun getPushNotificationService(): IPushNotificationService {
        return this.pushNotificationService
    }

}
