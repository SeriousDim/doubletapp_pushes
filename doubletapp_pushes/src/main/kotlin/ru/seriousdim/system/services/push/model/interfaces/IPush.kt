package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext

interface IPush {
    val context: SystemContext
    var text: String
    fun shouldBeFiltered(): Boolean;
}