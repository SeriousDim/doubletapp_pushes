package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext

interface ITechPush: IPush {
    var osVersion: Int;

    override fun shouldBeFiltered(): Boolean {
        return context.osVersion > this.osVersion
    }
}