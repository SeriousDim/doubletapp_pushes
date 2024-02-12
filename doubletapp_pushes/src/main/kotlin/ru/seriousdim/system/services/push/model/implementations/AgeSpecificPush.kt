package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.interfaces.IAgePush
import ru.seriousdim.system.services.push.model.interfaces.IExpiringPush

data class AgeSpecificPush(
    override var text: String,
    override var expiryDate: Long,
    override var age: Int
): IExpiringPush, IAgePush;
