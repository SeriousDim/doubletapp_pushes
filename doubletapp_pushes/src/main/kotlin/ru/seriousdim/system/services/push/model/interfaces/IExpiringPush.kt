package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext

interface IExpiringPush: IPush {
    var expiryDate: Long;

    override fun shouldBeFiltered(): Boolean {
        return this.expiryDate < context.time
    }
}