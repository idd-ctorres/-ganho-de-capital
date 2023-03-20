import file.StreamReadWriteStrategyFactory
import file.read
import file.write
import model.Operation
import util.Logger
import java.io.ByteArrayOutputStream

fun main(args: Array<String>) {

    val operations = readOperations(args)

    process(operations)
}

fun readOperations(args: Array<String>) : ArrayList<Array<Operation>> {

    val operations : ArrayList<Array<Operation>> = arrayListOf()
    val readStrategy = StreamReadWriteStrategyFactory.ofType("json")

    do {
        val input = readlnOrNull()

        input?.trim()?.takeIf { input.isNotEmpty() }?.also {
            operations.add( readStrategy.read( input.trimIndent().byteInputStream() ) )
        }

    } while (!input.isNullOrEmpty())

    return operations
}

fun process(operations: ArrayList<Array<Operation>>) {

    val operationProcessor = OperationProcessor()

    if (operations.isEmpty()) {
        Logger.info("Nenhuma operação foi informada.")
    }

    operations.map {
        Logger.debug()
        Logger.debug("¬¬¬¬¬ Processando operações [ ${it.joinToString(",")} ]")
        Logger.debug()
        val proccess = operationProcessor.process(it.asList())
        Logger.debug("*********************************************************************************************************")

        proccess
    }.map{
        StreamReadWriteStrategyFactory.ofType("json").let { strategy ->
            val outputStream = ByteArrayOutputStream()

            strategy.write(outputStream, it.toTypedArray())

            outputStream.toString()
        }
    }.forEach {
        Logger.info(it)
    }

}