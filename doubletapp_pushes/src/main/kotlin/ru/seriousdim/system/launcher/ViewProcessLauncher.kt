package ru.seriousdim.system.launcher

import ru.seriousdim.system.view.console.IViewProcess

class ViewProcessLauncher(
    val view: IViewProcess
): ILauncher {
    override fun launch() {
        view.onContextCreate()
        view.onStart()
    }
}