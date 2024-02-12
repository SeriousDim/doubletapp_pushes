package ru.seriousdim.system.services.push.model.implementations

import ru.seriousdim.system.services.push.model.Gender
import ru.seriousdim.system.services.push.model.interfaces.IGenderPush

data class GenderPush(
    override var text: String,
    override var gender: Gender
): IGenderPush;
