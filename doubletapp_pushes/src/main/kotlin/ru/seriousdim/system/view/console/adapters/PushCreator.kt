package ru.seriousdim.system.view.console.adapters

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.services.push.model.implementations.*
import ru.seriousdim.system.services.push.model.interfaces.IPush

class PushCreator(
    var context: SystemContext
) {
    private val typeMappers = mapOf<String, (PushFieldAdapter) -> IPush>(
        "LocationPush" to { fields -> LocationPush(
            context,
            fields.text!!,
            fields.x!!,
            fields.y!!,
            fields.radius!!,
            fields.expiryDate!!
        ) },
        "AgeSpecificPush" to { fields -> AgeSpecificPush(
            context,
            fields.text!!,
            fields.expiryDate!!,
            fields.age!!
        ) },
        "TechPush" to { fields -> TechPush(
            context,
            fields.text!!,
            fields.osVersion!!
        ) },
        "LocationAgePush" to { fields -> LocationAgePush(
            context,
            fields.text!!,
            fields.x!!,
            fields.y!!,
            fields.radius!!,
            fields.age!!
        ) },
        "GenderAgePush" to { fields -> GenderAgePush(
            context,
            fields.text!!,
            fields.gender!!,
            fields.age!!
        ) },
        "GenderPush" to { fields -> GenderPush(
            context,
            fields.text!!,
            fields.gender!!
        ) },
    )

    fun transformFields(lines: List<String>): PushFieldAdapter {
        val fields = PushFieldAdapter()

        for (line in lines) {
            val words = line.split(' ')
            fields.setField(words[0], words[1])
        }

        return fields
    }

    fun createPush(lines: List<String>): IPush {
        val fields = this.transformFields(lines)
        return typeMappers[fields.type]?.invoke(fields)!!
    }
}