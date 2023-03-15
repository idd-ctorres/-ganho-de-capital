import file.read.StreamReadStrategyFactory
import file.read.read
import model.Operation
import model.OperationType

fun main(args: Array<String>) {
    val operations : Array<Operation> = StreamReadStrategyFactory.ofType("json").let { it ->
        val stream = """
            [
                {"operation":"buy", "unit-cost":20.00, "quantity": 10},
                {"operation":"sell", "unit-cost":19.00,  "quantity": 5},
                {"operation":"buy", "unit-cost":10.00, "quantity": 5}
            ]
            """.trimIndent().byteInputStream()

        it.read(stream)
    }

    // OperationConsolidation
    var mediaPonderada = 0.0;
    var qtAcoesAtual = 0.0;

    operations.forEach { operation ->

        if (operation.operation == OperationType.BUY) {
            mediaPonderada = if (mediaPonderada == 0.0) {
                operation.unitCost
            } else {
                val mediaPonderadaNova =
                    ((qtAcoesAtual * mediaPonderada) + (operation.quantity * operation.unitCost)) / (qtAcoesAtual + operation.quantity)
                println("mediaPonderada = (($qtAcoesAtual * $mediaPonderada) + (${operation.quantity} * ${operation.unitCost})) / ($qtAcoesAtual + ${operation.quantity})")
                mediaPonderadaNova
            }

            qtAcoesAtual += operation.quantity

            println("Média ponderada = $mediaPonderada")
        }

        if (operation.operation == OperationType.SELL) {
            qtAcoesAtual -= operation.quantity
            val resultado = (operation.unitCost - mediaPonderada) * operation.quantity

            if (resultado > 0) {
                println("Lucro: $resultado")
            } else {
                println("Prejuízo: $resultado")
            }
        }

    }

    operations.forEach { println(it) }

//    print(Config.getInstance().tax.minOperation)
}