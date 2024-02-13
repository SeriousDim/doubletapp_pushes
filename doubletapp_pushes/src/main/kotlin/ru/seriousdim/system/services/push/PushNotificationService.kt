package ru.seriousdim.system.services.push

import ru.seriousdim.system.services.push.model.interfaces.IPush
import java.util.LinkedList

open class PushNotificationService: IPushNotificationService {
    private val pushes = LinkedList<IPush>()

    override fun appendPush(push: IPush) {
        this.pushes.addLast(push)
    }

    override fun filterPushes() {
        val iterator = this.pushes.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (item.shouldBeFiltered()) {
                iterator.remove()
            }
        }
    }

    override fun getPushList(): List<IPush> {
        return this.pushes
    }

}