package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.services.push.model.interfaces.IAgePush
import ru.seriousdim.system.services.push.model.interfaces.IExpiringPush

data class AgeSpecificPush(
    override val context: SystemContext,
    override var text: String,
    override var expiryDate: Long,
    override var age: Int
): IExpiringPush, IAgePush {
    override fun shouldBeFiltered(): Boolean {
        return super<IExpiringPush>.shouldBeFiltered()
                && super<IAgePush>.shouldBeFiltered()
    }
}
