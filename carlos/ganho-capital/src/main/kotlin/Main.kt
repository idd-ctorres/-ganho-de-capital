import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    while (scanner.hasNextLine()) {
        val nextLine = scanner.nextLine()
        if (nextLine.isBlank()) break
        println(ProcessadorOperacoes.processarOperacoes(nextLine))
    }

    scanner.close()
}

