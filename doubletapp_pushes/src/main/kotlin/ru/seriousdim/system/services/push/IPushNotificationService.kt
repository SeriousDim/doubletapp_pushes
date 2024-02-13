package ru.seriousdim.system.services.push

import ru.seriousdim.system.services.push.model.interfaces.IPush

interface IPushNotificationService {
    fun appendPush(push: IPush)
    fun filterPushes()

    fun getPushList(): List<IPush>
}