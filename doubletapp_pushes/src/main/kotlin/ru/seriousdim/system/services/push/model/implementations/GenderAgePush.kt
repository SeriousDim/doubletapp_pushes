package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.Gender
import ru.seriousdim.system.services.push.model.interfaces.IAgePush
import ru.seriousdim.system.services.push.model.interfaces.IGenderPush

data class GenderAgePush(
    override var text: String,
    override var gender: Gender,
    override var age: Int
): IAgePush, IGenderPush;
