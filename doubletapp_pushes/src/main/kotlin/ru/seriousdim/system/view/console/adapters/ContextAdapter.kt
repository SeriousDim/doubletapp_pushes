package ru.seriousdim.system.view.console.adapters

import ru.seriousdim.system.model.context.SystemContext

class ContextAdapter(
    lines: List<String>
): SystemContext() {
    private val setters = mapOf<String, (String) -> Unit>(
        "time" to { value -> this.time = value.toLong() },
        "gender" to { value -> this.user.gender = GenderMapper.fromString(value) },
        "age" to { value -> this.user.age = value.toInt() },
        "os_version" to { value -> this.osVersion = value.toInt() },
        "x_coord" to { value -> this.getGestureListenerService().setX(value.toFloat()) },
        "y_coord" to { value -> this.getGestureListenerService().setY(value.toFloat()) }
    )

    init {
        this.transformData(lines)
    }

    private fun transformData(lines: List<String>) {
        for (line in lines) {
            val words = line.split(' ')
            val setter = setters[words[0]]
            setter?.invoke(words[1])
        }
    }
}