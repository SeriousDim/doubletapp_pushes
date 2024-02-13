package ru.seriousdim.system.view.console

import ru.seriousdim.system.model.context.SystemContext

interface IViewProcess {
    fun onContextCreate()

    fun onStart()
}