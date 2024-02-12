package ru.seriousdim.system.services.push.model.interfaces

import ru.seriousdim.system.services.push.model.Gender

interface IGenderPush: IPush {
    var gender: Gender;
}