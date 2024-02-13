package ru.seriousdim.system.view.console

import ru.seriousdim.system.model.context.SystemContext
import ru.seriousdim.system.view.console.adapters.ContextAdapter
import ru.seriousdim.system.view.console.adapters.PushCreator
import java.io.PrintStream
import java.util.Scanner

class ConsoleView(
    var inputReader: Scanner,
    var outputStream: PrintStream
): IViewProcess {
    private val CONTEXT_LINES_AMOUNT = 6

    private lateinit var context: SystemContext

    override fun onContextCreate() {
        val contextLines = mutableListOf<String>()

        for (lineNumber in 1..CONTEXT_LINES_AMOUNT) {
            contextLines.add(inputReader.nextLine())
        }

        this.context = ContextAdapter(contextLines)
    }

    override fun onStart() {
        val pushCreator = PushCreator(context)

        val pushAmount = inputReader.nextLine().toInt()

        for (push in 1..pushAmount) {
            val paramAmount = inputReader.nextLine().toInt()

            val params = mutableListOf<String>()

            for (param in 1..paramAmount) {
                params.add(inputReader.nextLine())
            }

            val newPush = pushCreator.createPush(params)
            context.getPushNotificationService().appendPush(newPush)
        }

        context.getPushNotificationService().filterPushes()

        val pushes = context.getPushNotificationService().getPushList()

        if (pushes.isEmpty()) {
            outputStream.println(-1)
        }

        for (push in pushes) {
            outputStream.println(push.text)
        }
    }
}