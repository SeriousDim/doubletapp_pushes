package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.interfaces.ITechPush

data class TechPush(
    override var text: String,
    override var osVersion: Int
): ITechPush;
