package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.model.user.Gender

interface IGenderPush: IPush {
    var gender: Gender;

    override fun shouldBeFiltered(): Boolean {
        val user = context.user;
        return user.gender != this.gender
    }
}