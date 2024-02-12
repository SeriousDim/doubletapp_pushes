package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.interfaces.IAgePush
import ru.seriousdim.system.services.push.model.interfaces.ILocationPush

data class LocationAgePush(
    override var text: String,
    override var x: Float,
    override var y: Float,
    override var radius: Int,
    override var age: Int
): ILocationPush, IAgePush;
