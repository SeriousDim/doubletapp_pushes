package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.interfaces.IExpiringPush
import ru.seriousdim.system.services.push.model.interfaces.ILocationPush

data class LocationPush(
    override var text: String,
    override var x: Float,
    override var y: Float,
    override var radius: Int,
    override var expiryDate: Long
): ILocationPush, IExpiringPush;
