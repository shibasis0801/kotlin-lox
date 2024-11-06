package dev.shibasis.learn

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

object Lox {
    var hadError = false
    fun report(line: Int, where: String, message: String) {
        System.err.println("[line $line] Error $where: $message")
        hadError = true
    }

    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        tokens.forEach {
            println(it)
        }
    }

    fun runFile(fileName: String) {
        val bytes = Files.readAllBytes(Paths.get(fileName))
        run(String(bytes))
        if (hadError) exitProcess(65)
    }

    fun runPrompt() {
        while(true) {
            println("> ")
            val line = readlnOrNull() ?: break
            run(line)
            hadError = false
        }
    }
}