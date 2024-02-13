package ru.seriousdim.system.launcher

import ru.seriousdim.system.view.console.ConsoleView
import java.util.Scanner

class SystemLauncher: ILauncher {
    override fun launch() {
        val inputReader = Scanner(System.`in`)
        val outputStream = System.out

        val view = ConsoleView(inputReader, outputStream)
        val viewProcessLauncher = ViewProcessLauncher(view)

        viewProcessLauncher.launch()
    }
}