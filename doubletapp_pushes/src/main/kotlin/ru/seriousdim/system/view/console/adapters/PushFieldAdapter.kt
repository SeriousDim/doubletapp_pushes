package ru.seriousdim.system.view.console.adapters

import ru.seriousdim.system.model.user.Gender

class PushFieldAdapter {
    var type: String? = null
    var text: String? = null
    var expiryDate: Long? = null
    var gender: Gender? = null
    var age: Int? = null
    var x: Float? = null
    var y: Float? = null
    var radius: Int? = null
    var osVersion: Int? = null

    private val mappers = mapOf<String, (String) -> Unit>(
        "type" to { value -> this.type = value },
        "text" to { value -> this.text = value },
        "expiry_date" to { value -> this.expiryDate = value.toLong() },
        "gender" to { value -> this.gender = GenderMapper.fromString(value) },
        "age" to { value -> this.age = value.toInt() },
        "x_coord" to { value -> this.x = value.toFloat() },
        "y_coord" to { value -> this.y = value.toFloat() },
        "radius" to { value -> this.radius = value.toInt() },
        "os_version" to { value -> this.osVersion = value.toInt() }
    )

    fun setField(field: String, value: String) {
        mappers[field]?.invoke(value)
    }
}