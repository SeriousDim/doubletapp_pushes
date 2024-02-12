package ru.seriousdim.system.view.console.adapters

import ru.seriousdim.system.model.user.Gender

object GenderMapper {
    fun fromString(value: String): Gender {
        when (value) {
            "m" -> Gender.MALE
            "f" -> Gender.FEMALE
        }
        return Gender.valueOf(value)
    }

    fun toString(gender: Gender): String {
        when (gender) {
            Gender.MALE -> "m"
            Gender.FEMALE -> "f"
        }
        return ""
    }
}