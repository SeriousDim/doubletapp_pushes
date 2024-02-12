package ru.seriousdim.system.view.console.adapters

interface IConsoleAdapter<T> {
    fun transformFromInput(input: List<String>): T

    fun transformToOutput(data: T): List<String>
}