package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.services.push.model.interfaces.ITechPush

data class TechPush(
    override val context: SystemContext,
    override var text: String,
    override var osVersion: Int
): ITechPush
