package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext

interface IAgePush: IPush {
    var age: Int;

    override fun shouldBeFiltered(): Boolean {
        val user = context.user
        return this.age > user.age
    }
}
