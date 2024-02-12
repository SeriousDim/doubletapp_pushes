package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.model.user.Gender
import ru.seriousdim.system.services.push.model.interfaces.IAgePush
import ru.seriousdim.system.services.push.model.interfaces.IGenderPush

data class GenderAgePush(
    override val context: SystemContext,
    override var text: String,
    override var gender: Gender,
    override var age: Int
): IAgePush, IGenderPush {
    override fun shouldBeFiltered(): Boolean {
        return super<IAgePush>.shouldBeFiltered()
                && super<IGenderPush>.shouldBeFiltered()
    }
}
