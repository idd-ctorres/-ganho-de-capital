import model.Operation
import model.OperationConsolidation
import model.OperationResults
import tax.BuyTaxCalculator
import tax.SellTaxCalculator
import tax.TaxCalculator
import util.Logger
import java.util.*

class OperationProcessor {

    // Lista de estratégias
    private val strategies : Collection<TaxCalculator> = listOf(
        BuyTaxCalculator(),
        SellTaxCalculator()
    )

    fun process(operations: Collection<Operation>) : Collection<OperationResults> {

        val consolidation = OperationConsolidation()
        val operationResults = arrayListOf<OperationResults>()

        operations.forEach {operation ->

            Logger.debug("Operação [ $operation ]")

            val tax = strategies.firstOrNull { it.handle(operation) }?.calculate(operation, consolidation)
                ?: throw RuntimeException("Invalid operation type")

            val results = OperationResults(tax)

            operationResults.add(results)

            Logger.debug()
        }

        return Collections.unmodifiableList(operationResults)
    }



}